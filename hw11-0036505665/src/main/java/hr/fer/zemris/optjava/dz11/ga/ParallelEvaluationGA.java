package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.*;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.*;

/**
 * A generational GA implementation where solution evaluation is parallelized.
 *
 * @author Bruna Dujmović
 *
 */
public class ParallelEvaluationGA {

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
     * The best solution found by the algorithm.
     */
    private GASolution<int[]> best;

    /**
     * The random number generator to use.
     */
    private IRNG rng; // TODO use in operator implementations

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
    public ParallelEvaluationGA(int populationSize, double minFitness, int maxIterations, int rectangleCount,
                                GrayScaleImage image, ISelection<int[]> selection, ICrossover<int[]> crossover,
                                IMutation<int[]> mutation, IGAEvaluator<int[]> evaluator) {
        this.populationSize = populationSize;
        this.minFitness = minFitness;
        this.maxIterations = maxIterations;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.evaluator = evaluator;
        this.rng = RNG.getRNG();
    }

    /**
     * Executes the algorithm.
     */
    public GASolution<int[]> run() {
        List<GASolution<int[]>> population = new ArrayList<>(populationSize);
        List<GASolution<int[]>> nextPopulation = new ArrayList<>(populationSize);

        initialize(population);

        // TODO threads

        int iteration = 0;
        while (iteration < maxIterations && best.fitness < minFitness){
            // TODO print best

            for (int i = 0; i < populationSize / 2; i++) {
                GASolution<int[]> firstParent = selection.from(population);
                GASolution<int[]> secondParent = selection.from(population);
                Collection<GASolution<int[]>> children = crossover.of(firstParent, secondParent);

                for (GASolution<int[]> child : children) {
                    mutation.mutate(child);
                    evaluator.evaluate(child);
                    nextPopulation.add(child);

                    if (child.fitness > best.fitness) {
                        best = child;
                    }
                }
            }

            population = nextPopulation;
            nextPopulation.clear();
        }

        return best;
    }

    /**
     * Initializes the given population with random solutions.
     *
     * @param population the population to initialize
     */
    private void initialize(Collection<GASolution<int[]>> population) {
        for (int i = 0; i < populationSize; i++) {
            int[] data = new int[1 + rectangleCount * 5];
            data[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            for (int j = 1; j < 1 + rectangleCount * 5; j += 5) {
                data[j] = rng.nextInt(0, image.getWidth());
                data[j + 1] = rng.nextInt(0, image.getHeight());
                data[j + 2] = rng.nextInt(1, image.getWidth());
                data[j + 3] = rng.nextInt(1, image.getHeight());
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
}
