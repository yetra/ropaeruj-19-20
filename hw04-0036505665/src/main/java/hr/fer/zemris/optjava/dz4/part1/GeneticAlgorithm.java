package hr.fer.zemris.optjava.dz4.part1;

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
     * The selection type to be used on the population.
     */
    private ISelection selection;

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
     * @param selection the selection type to be used on the population
     * @param function the function that is being optimized
     */
    public GeneticAlgorithm(int populationSize, double minError, int maxIterations, ISelection selection,
                            IFunction function) {
        this.populationSize = populationSize;
        this.minError = minError;
        this.maxIterations = maxIterations;
        this.selection = selection;
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

            Chromosome best = Collections.max(population);
            newGeneration.add(best); // elitism

            for (int i = 0; i < populationSize / 2; i++) {
                Chromosome firstParent = selection.from(population);
                Chromosome secondParent = selection.from(population);

                Collection<Chromosome> children = cross(firstParent, secondParent);
                mutate(children);

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

    /**
     * Performs a crossover on the given parent chromosomes.
     *
     * @param firstParent the first parent chromosome
     * @param secondParent the second parent chromosome
     * @return a collection of two child chromosomes obtained by crossing the parents
     */
    private Collection<Chromosome> cross(Chromosome firstParent, Chromosome secondParent) {
        return null;
    }

    /**
     * Mutates the given collection of child chromosomes.
     *
     * @param children the collection of child chromosomes to mutate
     */
    private void mutate(Collection<Chromosome> children) {

    }
}
