package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.ga.Chromosome;
import hr.fer.zemris.optjava.dz4.ga.crossover.BLXAlphaCrossover;
import hr.fer.zemris.optjava.dz4.ga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz4.ga.function.IFunction;
import hr.fer.zemris.optjava.dz4.ga.function.TransferFunction;
import hr.fer.zemris.optjava.dz4.ga.mutation.GaussianMutation;
import hr.fer.zemris.optjava.dz4.ga.mutation.IMutation;
import hr.fer.zemris.optjava.dz4.ga.selection.ISelection;
import hr.fer.zemris.optjava.dz4.ga.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz4.ga.selection.TournamentSelection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * An implementation of a generational genetic algorithm with elitism.
 *
 * @author Bruna Dujmović
 *
 */
public class GeneticAlgorithm {

    /**
     * The size of the population.
     */
    private int populationSize;

    /**
     * The minimum error value which, if reached, will terminate the algorithm.
     */
    private double minError;

    /**
     * The maximum number of iterations before the algorithm terminates.
     */
    private int maxIterations;

    /**
     * The selection to be used on the population.
     */
    private ISelection selection;

    /**
     * The crossover to be used on the population.
     */
    private ICrossover crossover;

    /**
     * The mutation to be used on the population.
     */
    private IMutation mutation;

    /**
     * The function that is being optimized.
     */
    private IFunction function;

    /**
     * Constructs a {@link GeneticAlgorithm} object with the given parameters.
     *
     * @param populationSize the size of the population
     * @param minError the minimum error value which, if reached, will terminate the algorithm
     * @param maxIterations the maximum number of iterations before the algorithm terminates
     * @param selection the selection to be used on the population
     * @param crossover the crossover to be used on the population
     * @param mutation the mutation to be used on the population
     * @param function the function that is being optimized
     */
    public GeneticAlgorithm(int populationSize, double minError, int maxIterations, ISelection selection,
                            ICrossover crossover, IMutation mutation, IFunction function) {
        this.populationSize = populationSize;
        this.minError = minError;
        this.maxIterations = maxIterations;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.function = function;
    }

    /**
     * Executes the genetic algorithm.
     */
    public void run() {
        List<Chromosome> population = new ArrayList<>(populationSize);

        initialize(population);
        evaluate(population);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            Chromosome best = Collections.max(population);
            System.out.println("Solution " + iteration + ": f(" + Arrays.toString(best.values) + ") = " + best.fitness);

            if (best.fitness <= minError) {
                return;
            }

            List<Chromosome> newGeneration = new ArrayList<>(populationSize);
            newGeneration.add(best); // elitism

            for (int i = 0; i < populationSize / 2; i++) {
                Chromosome firstParent = selection.from(population);
                Chromosome secondParent = selection.from(population);
                
                Collection<Chromosome> children = crossover.of(firstParent, secondParent);
                children.forEach(child -> mutation.mutate(child));

                newGeneration.addAll(children);
            }

            population = newGeneration;
            evaluate(population);
        }
    }

    /**
     * Initializes the given population with random chromosomes.
     *
     * @param population the population to initialize
     */
    private void initialize(Collection<Chromosome> population) {
        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome());
        }
    }

    /**
     * Evaluates the given population.
     *
     * @param population the population to evaluate
     */
    private void evaluate(Collection<Chromosome> population) {
        for (Chromosome chromosome : population) {
            chromosome.fitness = function.valueAt(chromosome.values);
        }
    }

    /**
     * The main method. Uses this GA implementation for finding the coefficients of a transfer
     * function whose formula and system response are known.

     * The {@link #populationSize}, {@link #minError}, {@link #maxIterations}, type of selection to
     * use, and standard deviation for {@link GaussianMutation} are all given as command-line arguments.
     *
     *
     * @param args the command-line arguments, 5 expected
     */
    public static void main(String[] args) {
        if (args.length != 5)  {
            System.out.println("Expected 5 arguments, got " + args.length);
            System.exit(1);
        }

        try {
            int populationSize = Integer.parseInt(args[0]);
            double minError = Double.parseDouble(args[1]);
            int maxIterations = Integer.parseInt(args[2]);
            ISelection selection = parseSelectionType(args[3]);
            double sigma = Double.parseDouble(args[4]);
            Path filePath = Paths.get("02-zad-prijenosna.txt");

            GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, minError, maxIterations, selection,
                    new BLXAlphaCrossover(), new GaussianMutation(sigma), TransferFunction.fromFile(filePath));

            ga.run();

        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns an implementation of {@link ISelection}.
     * The given string determines which implementation is returned.
     *
     * @param selectionType the {@link ISelection} implementation to return
     * @return an implementation of {@link ISelection} determined by the given string
     * @throws IllegalArgumentException if the given selection type string is invalid
     */
    private static ISelection parseSelectionType(String selectionType) {
        if (selectionType.equals("rouletteWheel")) {
            return new RouletteWheelSelection();
        }

        if (selectionType.startsWith("tournament:")) {
            int tournamentSize = Integer.parseInt(selectionType.split(":")[1]);
            return new TournamentSelection(tournamentSize);
        }

        throw new IllegalArgumentException("Unknown selection type " + selectionType);
    }
}
