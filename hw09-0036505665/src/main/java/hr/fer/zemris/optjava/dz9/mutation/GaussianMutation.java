package hr.fer.zemris.optjava.dz9.mutation;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of Gaussian mutation which adds a Gaussian distributed random value
 * to each value of the genome.
 *
 * @author Bruna Dujmović
 *
 */
public class GaussianMutation implements Mutation {

    /**
     * The probability of mutating a solution's component.
     */
    private double probability;

    /**
     * The standard deviation of the distribution.
     */
    private double sigma;

    /**
     * The lowest possible values of each dimension in the solution space.
     */
    private double[] mins;

    /**
     * The highest possible values of each dimension in the solution space.
     */
    private double[] maxs;

    /**
     * Constructs a {@link GaussianMutation} instance.
     *
     * @param probability the probability of mutating a solution's component
     * @param sigma the standard deviation of the distribution
     * @param mins the lowest possible values of each dimension in the solution space
     * @param maxs the highest possible values of each dimension in the solution space
     */
    public GaussianMutation(double probability, double sigma, double[] mins, double[] maxs) {
        this.probability = probability;
        this.sigma = sigma;
        this.mins = mins;
        this.maxs = maxs;
    }

    @Override
    public void mutate(double[][] solutions) {
        for (int i = 0; i < solutions.length; i++) {
            for (int j = 0; j < solutions[0].length; j++) {
                if (probability >= ThreadLocalRandom.current().nextDouble()) {
                    solutions[i][j] += ThreadLocalRandom.current().nextGaussian() * sigma;

                    if (solutions[i][j] < mins[j]) {
                        solutions[i][j] = mins[j];
                    } else if (solutions[i][j] > maxs[j]) {
                        solutions[i][j] = maxs[j];
                    }
                }
            }
        }
    }
}
