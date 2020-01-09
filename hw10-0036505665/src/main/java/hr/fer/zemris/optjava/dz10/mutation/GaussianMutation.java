package hr.fer.zemris.optjava.dz10.mutation;

import hr.fer.zemris.optjava.dz10.Solution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of Gaussian mutation which adds a Gaussian distributed random value
 * to each variable of a given solution.
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
     * The lowest possible values of each solution variable.
     */
    private double[] mins;

    /**
     * The highest possible values of each solution variable.
     */
    private double[] maxs;

    /**
     * Constructs a {@link GaussianMutation} instance.
     *
     * @param probability the probability of mutating a solution's component
     * @param sigma the standard deviation of the distribution
     * @param mins the lowest possible values of each solution variable
     * @param maxs the highest possible values of each solution variable
     */
    public GaussianMutation(double probability, double sigma, double[] mins, double[] maxs) {
        this.probability = probability;
        this.sigma = sigma;
        this.mins = mins;
        this.maxs = maxs;
    }

    @Override
    public void mutate(Solution[] solutions) {
        for (Solution solution : solutions) {
            for (int i = 0; i < solutions[0].variables.length; i++) {
                if (probability >= ThreadLocalRandom.current().nextDouble()) {
                    solution.variables[i] += ThreadLocalRandom.current().nextGaussian() * sigma;

                    if (solution.variables[i] < mins[i]) {
                        solution.variables[i] = mins[i];
                    } else if (solution.variables[i] > maxs[i]) {
                        solution.variables[i] = maxs[i];
                    }
                }
            }
        }
    }
}
