package hr.fer.zemris.optjava.dz8.de;

import hr.fer.zemris.optjava.dz8.de.crossover.Crossover;
import hr.fer.zemris.optjava.dz8.de.mutation.Mutation;
import hr.fer.zemris.optjava.dz8.function.Function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of the Differential Evolution algorithm to be used for function minimization.
 *
 * This implementation supports different kinds of trial vector generation strategies.
 * The strategy can be adjusted by passing different {@link Mutation} & {@link Crossover} instances to the constructor.
 *
 * @author Bruna Dujmović
 *
 */
public class DE {

    /**
     * The function to minimize.
     */
    private Function function;

    /**
     * The number of dimensions of the {@link #function}.
     */
    private int dimensions;

    /**
     * The size of the vector population.
     */
    private int populationSize;

    /**
     * The maximum number of algorithm iterations.
     */
    private int maxIterations;

    /**
     * The error threshold which allows for algorithm termination before {@link #maxIterations} is reached.
     */
    private double errorThreshold;

    /**
     * The mutation to use in the algorithm.
     */
    private Mutation mutation;

    /**
     * The crossover to use in the algorithm.
     */
    private Crossover crossover;

    /**
     * The lowest allowed values for each vector component.
     */
    private double[] lowerBounds;

    /**
     * The highest allowed values for each vector component.
     */
    private double[] upperBounds;

    /**
     * The best vector that was found by the algorithm.
     */
    private double[] best;

    /**
     * The error value of the best vector.
     */
    private double bestError;

    /**
     * The error values of the vector population.
     */
    private double[] errors;

    /**
     * Constructs a {@link DE} object.
     *
     * @param function the function to minimize
     * @param dimensions the number of dimensions of the {@link #function}
     * @param populationSize the size of the vector population
     * @param maxIterations the maximum number of algorithm iterations
     * @param errorThreshold the error threshold which allows for algorithm termination before {@link #maxIterations}
     *                       is reached
     * @param mutation the mutation to use in the algorithm
     * @param crossover the crossover to use in the algorithm
     * @param lowerBounds the lowest allowed values for each vector component
     * @param upperBounds the highest allowed values for each vector component
     */
    public DE(Function function, int dimensions, int populationSize, int maxIterations, double errorThreshold,
              Mutation mutation, Crossover crossover, double[] lowerBounds, double[] upperBounds) {
        this.function = function;
        this.dimensions = dimensions;

        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.errorThreshold = errorThreshold;

        this.mutation = mutation;
        this.crossover = crossover;

        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    /**
     * Executes the algorithm.
     *
     * @return the best vector that was found by the algorithm
     */
    public double[] run() {
        double[][] vectors = new double[populationSize][dimensions];
        initialize(vectors);
        evaluate(vectors);

        double[][] trialVectors = new double[populationSize][];

        int iteration = 0;
        while (iteration < maxIterations && bestError > errorThreshold) {
            for (int i = 0; i < vectors.length; i++) {
                double[] targetVector = vectors[i];
                double[] mutantVector = mutation.of(vectors, best, i);

                trialVectors[i] = crossover.of(targetVector, mutantVector);
            }

            for (int i = 0; i < populationSize; i++) {
                double trialValue = function.valueAt(trialVectors[i]);

                if (trialValue <= errors[i]) {
                    vectors[i] = trialVectors[i];
                    errors[i] = trialValue;

                    if (errors[i] <= bestError) {
                        best = Arrays.copyOf(vectors[i], vectors[i].length);
                        bestError = errors[i];
                    }
                }
            }

            System.out.println("Iteration " + iteration + ", error: " + bestError);
            iteration++;
        }

        System.out.println("\nBest: " + Arrays.toString(best));
        System.out.println("Best error: " + bestError + "\n");
        return best;
    }

    /**
     * Initializes the given vector population.
     *
     * @param vectors the vector population to initialize
     */
    private void initialize(double[][] vectors) {
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < dimensions; j++) {
                vectors[i][j] = ThreadLocalRandom.current().nextDouble(lowerBounds[i], upperBounds[i]);
            }
        }
    }

    /**
     * Evaluates the given vector population and updates {@link #best} & {@link #bestError} accordingly.
     *
     * @param vectors the vector population to evaluate
     */
    private void evaluate(double[][] vectors) {
        if (errors == null) {
            errors = new double[populationSize];
        }

        for (int i = 0; i < populationSize; i++) {
            errors[i] = function.valueAt(vectors[i]);

            if (best == null || errors[i] < bestError) {
                best = Arrays.copyOf(vectors[i], vectors[i].length);
                bestError = errors[i];
            }
        }
    }
}
