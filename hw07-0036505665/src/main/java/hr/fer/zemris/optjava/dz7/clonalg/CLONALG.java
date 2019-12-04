package hr.fer.zemris.optjava.dz7.clonalg;

import hr.fer.zemris.optjava.dz7.function.Function;

/**
 * An implementation of the CLONALG clonal selection algorithm.
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
}


