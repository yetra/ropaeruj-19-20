package hr.fer.zemris.optjava.dz7.clonalg;

import hr.fer.zemris.optjava.dz7.function.Function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of the CLONALG clonal selection algorithm to be used for function minimization.
 *
 * @author Bruna Dujmović
 *
 */
public class CLONALG {

    /**
     * The function to optimize.
     */
    private Function function;

    /**
     * The maximum number of algorithm iterations.
     */
    private int maxIterations;

    /**
     * The number of antibodies in the population.
     */
    private int populationSize;

    /**
     * The number of antibody clones.
     */
    private int clonesSize;

    /**
     * A parameter used to determine the clones population size.
     */
    private int beta;

    /**
     * The number of worst antibodies to replace with random ones in each algorithm iteration.
     */
    private int numberToReplace;

    /**
     * A parameter used for calculating the number of mutations to be applied to each antibody.
     */
    private int c;

    /**
     * The lowest allowed value for each antibody variable.
     */
    private double[] mins;

    /**
     * The highest allowed value for each antibody variable.
     */
    private double[] maxs;

    /**
     * Constructs a {@link CLONALG} instance of the given parameters.
     *
     * @param function the function to optimize
     * @param maxIterations the maximum number of algorithm iterations
     * @param populationSize the number of antibodies in the population
     * @param beta a parameter used to determine the clones population size
     * @param numberToReplace the number of worst antibodies to replace iteration
     * @param c a parameter for calculating the number of mutations for each antibody
     * @param mins the lowest allowed value for each antibody variable
     * @param maxs the highest allowed value for each antibody variable
     */
    public CLONALG(Function function, int maxIterations, int populationSize, int beta,
                   int numberToReplace, double[] mins, double[] maxs, int c) {
        this.function = function;

        this.maxIterations = maxIterations;
        this.populationSize = populationSize;
        this.beta = beta;
        for (int i = 1; i <= populationSize; i++) {
            clonesSize += (int) ((beta * populationSize) / ((double) i) + 0.5);
        }
        this.numberToReplace = numberToReplace;
        this.c = c;

        this.mins = mins;
        this.maxs = maxs;
    }

    /**
     * Executes the algorithm.
     */
    public double[] run() {
        Antibody[] population = new Antibody[populationSize];
        initialize(population);

        int iteration = 0;
        while(iteration < maxIterations) {
            evaluate(population);

            Antibody[] clones = clone(population);
            hypermutate(clones);
            evaluate(clones);

            select(clones, population);
            replace(population);

            iteration++;
        }

        return population[0].variables;
    }

    /**
     * Initializes the given population with {@link #populationSize} random antibodies.
     *
     * @param population the population to initialize.
     */
    private void initialize(Antibody[] population) {
        for (int i = 0; i < populationSize; i++) {
            population[i] = new Antibody(function.getDimensions(), mins, maxs);
        }
    }

    /**
     * Evaluates the antibodies in the given population.
     *
     * @param population the population to evaluate
     */
    private void evaluate(Antibody[] population) {
        for (Antibody antibody : population) {
            antibody.affinity = function.valueAt(antibody.variables);
        }
    }

    /**
     * Creates {@link #clonesSize} clones of the given population.
     * The number of clones for each antibody is proportional to its affinity.
     *
     * @param population the population to clone
     * @return a population of clones
     */
    private Antibody[] clone(Antibody[] population) {
        Antibody[] clones = new Antibody[clonesSize];
        Arrays.sort(population);

        int index = 0;
        for (int i = 1; i <= population.length; i++) {
            int numberOfCopies = (int) ((beta * populationSize) / ((double) i) + 0.5);

            for (int j = 0; j < numberOfCopies; j++) {
                clones[index] = population[i - 1].copy();
                index++;
            }
        }

        return clones;
    }

    /**
     * Hypermutates the given population of clones.
     * The number of mutations of each antibody is proportional to its affinity.
     * In each mutation a randomly selected antibody variable is incremented by a double in range [0, 1).
     *
     * @param clones the population of clones to hypermutate
     */
    private void hypermutate(Antibody[] clones) {
        double minAffinity = clones[clonesSize - 1].affinity;

        for (int i = 1; i < clones.length; i++) {
            int maxMutations = (int) (clones[i].affinity - minAffinity) * c * function.getDimensions();

            for (int mutation = 0; mutation < maxMutations; mutation++) {
                int randomIndex = ThreadLocalRandom.current().nextInt(clonesSize);

                clones[i].variables[randomIndex] += ThreadLocalRandom.current().nextDouble();
            }
        }
    }

    /**
     * Selects the {@link #populationSize} best clones and adds them to the specified population.
     *
     * @param clones the population of clones to select from
     * @param population the population in which the best clones will be inserted
     */
    private void select(Antibody[] clones, Antibody[] population) {
        Arrays.sort(clones);
        System.arraycopy(clones, 0, population, 0, population.length);
    }

    /**
     * Replaces the {@link #numberToReplace} worst antibodies in a given population with random antibodies.
     *
     * @param population the population whose worst antibodies will be replaced
     */
    private void replace(Antibody[] population) {
        int offset = populationSize - numberToReplace;

        for (int i = 0; i < numberToReplace; i++) {
            population[offset + i].randomize(mins, maxs);
        }
    }
}


