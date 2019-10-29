package hr.fer.zemris.optjava.dz4.part1.mutation;

import hr.fer.zemris.optjava.dz4.part1.Chromosome;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of gaussian mutation which adds a Gaussian distributed random value
 * to each value of the genome.
 *
 * @author Bruna Dujmović
 *
 */
public class GaussianMutation implements IMutation {

    /**
     * The standard deviation of the distribution.
     */
    private double sigma;

    /**
     * Consturcts a {@link GaussianMutation} of the given parameter.
     *
     * @param sigma the standard deviation of the distribution
     */
    public GaussianMutation(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public Chromosome of(Chromosome chromosome) {
        Chromosome copy = chromosome.copy();
        mutate(copy);

        return copy;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        for (int i = 0; i < chromosome.values.length; i++) {
            chromosome.values[i] += ThreadLocalRandom.current().nextGaussian() * sigma;
        }
    }
}
