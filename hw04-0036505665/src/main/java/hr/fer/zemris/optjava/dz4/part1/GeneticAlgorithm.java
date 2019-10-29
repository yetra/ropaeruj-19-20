package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.part1.crossover.ICrossover;
import hr.fer.zemris.optjava.dz4.part1.function.IFunction;
import hr.fer.zemris.optjava.dz4.part1.mutation.IMutation;
import hr.fer.zemris.optjava.dz4.part1.selection.ISelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of a generational genetic algorithm with elitism.
 *
 * @author Bruna Dujmović
 *
 */
public class GeneticAlgorithm {

    private static final int CHROMOSOME_SIZE = 10;

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
        Collection<Chromosome> population = new ArrayList<>(populationSize);

        initialize(population);
        evaluate(population);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<Chromosome> newGeneration = new ArrayList<>(populationSize);

            // elitism
            Chromosome best = Collections.max(population);
            newGeneration.add(best);

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
            population.add(new Chromosome(CHROMOSOME_SIZE));
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
}
