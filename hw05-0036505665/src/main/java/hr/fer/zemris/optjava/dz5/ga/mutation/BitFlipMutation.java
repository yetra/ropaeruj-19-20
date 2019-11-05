package hr.fer.zemris.optjava.dz5.ga.mutation;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link IMutation} implementation that flips the bits of a given {@link Chromosome}
 * with the given probability.
 *
 * @author Bruna Dujmović
 *
 */
public class BitFlipMutation implements IMutation<Boolean> {

    /**
     * The default bit flip probability.
     */
    private static final double DEFAULT_PROBABILITY = 0.005;

    /**
     * The bit flip probability.
     */
    private double probability;

    /**
     * Constructs a {@link BitFlipMutation} of the default bit flip probability.
     */
    public BitFlipMutation() {
        this.probability = DEFAULT_PROBABILITY;
    }

    /**
     * Constructs a {@link BitFlipMutation} of the given bit flip probability.
     *
     * @param probability the bit flip probability
     */
    public BitFlipMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public Chromosome<Boolean> of(Chromosome<Boolean> chromosome) {
        Chromosome<Boolean> copy = chromosome.copy();
        mutate(copy);

        return copy;
    }

    @Override
    public void mutate(Chromosome<Boolean> chromosome) {
        for (int i = 0; i < chromosome.values.length; i++) {
            if (probability <= ThreadLocalRandom.current().nextDouble()) {
                chromosome.values[i] = !chromosome.values[i];
            }
        }
    }
}
