package hr.fer.zemris.optjava.dz8.de;

import hr.fer.zemris.optjava.dz8.function.Function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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
     * The parameter determining the impact of the vector difference when constructing a mutant vector.
     */
    private double differentialWeight;

    /**
     * The probability of replacing a trial vector component with a mutant vector component.
     */
    private double crossoverProbability;

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
     * @param differentialWeight the parameter determining the impact of the vector difference when constructing a
     *                           mutant vector
     * @param crossoverProbability the probability of replacing a trial vector component with a mutant vector component
     * @param lowerBounds the lowest allowed values for each vector component
     * @param upperBounds the highest allowed values for each vector component
     */
    public DE(Function function, int dimensions, int populationSize, int maxIterations, double errorThreshold,
              double differentialWeight, double crossoverProbability, double[] lowerBounds, double[] upperBounds) {
        this.function = function;
        this.dimensions = dimensions;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.errorThreshold = errorThreshold;
        this.differentialWeight = differentialWeight;
        this.crossoverProbability = crossoverProbability;
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

        double[] mutant_vector = new double[dimensions];
        double[][] trial_vectors = new double[populationSize][dimensions];

        int iteration = 0;
        while (iteration < maxIterations && bestError > errorThreshold) {
            for (int i = 0; i < populationSize; i++) {
                int r0, r1, r2;
                do {
                    r0 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r0 == i);
                do {
                    r1 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r1 == i || r1 == r0);
                do {
                    r2 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r2 == r1 || r2 == r0 || r2 == i);

                double r = ThreadLocalRandom.current().nextDouble(dimensions);

                for (int j = 0; j < dimensions; j++) {
                    mutant_vector[j] = vectors[r0][j] + differentialWeight * (vectors[r1][j] - vectors[r2][j]);
                }

                for (int j = 0; j < dimensions; j++) {
                    double randomNumber = ThreadLocalRandom.current().nextDouble();

                    if (randomNumber <= crossoverProbability || j == r) {
                        trial_vectors[i][j] = mutant_vector[j];
                    } else {
                        trial_vectors[i][j] = vectors[i][j];
                    }
                }
            }

            for (int i = 0; i < populationSize; i++) {
                double trial_value = function.valueAt(trial_vectors[i]);

                if (trial_value < errors[i]) {
                    vectors[i] = trial_vectors[i];
                    errors[i] = trial_value;

                    if (errors[i] < bestError) {
                        best = Arrays.copyOf(vectors[i], vectors[i].length);
                        bestError = errors[i];
                    }
                }
            }

            System.out.println(Arrays.toString(best) + " - " + bestError);
            iteration++;
        }

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
