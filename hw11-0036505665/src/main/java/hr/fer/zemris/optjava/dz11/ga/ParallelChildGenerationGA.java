package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.*;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A generational GA implementation where child generation is parallelized.
 *
 * @author Bruna Dujmović
 *
 */
public class ParallelChildGenerationGA {

    /**
     * The size of the population.
     */
    private int populationSize;

    /**
     * The minimum fitness value which, if reached, will terminate the algorithm.
     */
    private double minFitness;

    /**
     * The maximum number of iterations before the algorithm terminates.
     */
    private int maxIterations;

    /**
     * The number of rectangles used for approximating the {@link #image}.
     */
    private int rectangleCount;

    /**
     * The image to approximate.
     */
    private GrayScaleImage image;

    /**
     * The selection to be used on the population.
     */
    private ISelection<int[]> selection;

    /**
     * The crossover to be used on the population.
     */
    private ICrossover<int[]> crossover;

    /**
     * The mutation to be used on the population.
     */
    private IMutation<int[]> mutation;

    /**
     * The evaluator to use for determining solutions' fitness values.
     */
    private IGAEvaluator<int[]> evaluator;

    /**
     * The population of solutions.
     */
    private List<GASolution<int[]>> population;

    /**
     * The best solution found by the algorithm.
     */
    private GASolution<int[]> best;

    /**
     * The random number generator to use.
     */
    private IRNG rng;

    /**
     * A queue containing generation tasks - the number of children to generate.
     */
    private LinkedBlockingQueue<Integer> generationTaskQueue;

    /**
     * A queue of collections of generated children.
     */
    private LinkedBlockingQueue<Collection<GASolution<int[]>>> generatedQueue;

    /**
     * Constructs a {@link ParallelEvaluationGA} object with the given parameters.
     *
     * @param populationSize the size of the population
     * @param minFitness the minimum fitness value which, if reached, will terminate the algorithm
     * @param maxIterations the maximum number of iterations before the algorithm terminates
     * @param selection the selection to be used on the population
     * @param crossover the crossover to be used on the population
     * @param mutation the mutation to be used on the population
     */
    public ParallelChildGenerationGA(int populationSize, double minFitness, int maxIterations, int rectangleCount,
                                     GrayScaleImage image, ISelection<int[]> selection, ICrossover<int[]> crossover,
                                     IMutation<int[]> mutation, IGAEvaluator<int[]> evaluator) {
        this.populationSize = populationSize;
        this.minFitness = minFitness;
        this.maxIterations = maxIterations;
        this.rectangleCount = rectangleCount;
        this.image = image;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.evaluator = evaluator;

        this.rng = RNG.getRNG();
        this.population = new ArrayList<>(populationSize);
        this.generationTaskQueue = new LinkedBlockingQueue<>();
        this.generatedQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Executes the algorithm.
     */
    public GASolution<int[]> run() throws InterruptedException {
        int workerCount = Runtime.getRuntime().availableProcessors();
        EVOThread[] workers = new EVOThread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new EVOThread(new GenerationJob());
            workers[i].start();
        }

        initialize();

        int iteration = 0;
        while (iteration < maxIterations && best.fitness < minFitness){
            System.out.println("Iteration " + iteration + ": " + best);

            addNewTasks(workerCount);

            List<GASolution<int[]>> nextPopulation = new ArrayList<>(populationSize);
            for (int i = 0; i < populationSize; i++) {
                Collection<GASolution<int[]>> generated = generatedQueue.take();
                nextPopulation.addAll(generated);
            }

            population = nextPopulation;

            iteration++;
        }

        for (int i = 0; i < workerCount; i++) {
            generationTaskQueue.put(0);
        }

        return best;
    }

    /**
     * Initializes the given population with random solutions.
     */
    private void initialize() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < populationSize; i++) {
            int[] data = new int[1 + 5 * rectangleCount];

            data[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            for (int j = 1; j < data.length; j += 5) {
                data[j] = rng.nextInt(0, width);
                data[j + 1] = rng.nextInt(0, height);
                data[j + 2] = rng.nextInt(1, width);
                data[j + 3] = rng.nextInt(1, height);
                data[j + 4] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            GASolution<int[]> solution = new IntArrayGASolution(data);

            evaluator.evaluate(solution);
            if (best == null || solution.fitness > best.fitness) {
                best = solution;
            }

            population.add(solution);
        }
    }

    /**
     * Adds new tasks to {@link #generationTaskQueue}.
     *
     * @param workerCount the number of workers
     * @throws InterruptedException if a thread is interrupted
     */
    private void addNewTasks(int workerCount) throws InterruptedException {
        int taskSize = populationSize / workerCount;
        int extra = populationSize - taskSize * workerCount;

        for (int i = 0; i < workerCount - 1; i++) {
            generationTaskQueue.put(taskSize);
        }

        generationTaskQueue.put(taskSize + extra);
    }

    /**
     * A job for creating a specified number of child solutions.
     */
    private class GenerationJob implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    int childrenToGenerate = generationTaskQueue.take();
                    if (childrenToGenerate == 0) {
                        break; // poison
                    }

                    Collection<GASolution<int[]>> generated = new ArrayList<>(childrenToGenerate);
                    for (int i = 0; i < childrenToGenerate / 2; i++) {
                        GASolution<int[]> firstParent = selection.from(population);
                        GASolution<int[]> secondParent = selection.from(population);
                        Collection<GASolution<int[]>> children = crossover.of(firstParent, secondParent);

                        for (GASolution<int[]> child : children) {
                            mutation.mutate(child);

                            evaluator.evaluate(child);
                            if (child.fitness > best.fitness) {
                                best = child;
                            }

                            generated.add(child);
                        }
                    }

                    generatedQueue.put(generated);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
