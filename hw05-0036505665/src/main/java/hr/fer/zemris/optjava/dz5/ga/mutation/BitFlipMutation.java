package hr.fer.zemris.optjava.dz5.ga.mutation;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

/**
 * An {@link IMutation} implementation that flips the bits of a given {@link Chromosome}
 * with the given probability.
 *
 * @author Bruna Dujmović
 *
 */
public class BitFlipMutation implements IMutation {

    /**
     * The default bit flip probability.
     */
    private static final double DEFAULT_PROBABILITY = 0.005;

    /**
     * Constructs a {@link BitFlipMutation} of the default probability.
     */
    public BitFlipMutation() {
    }

    @Override
    public Chromosome of(Chromosome chromosome) {
        return null;
    }

    @Override
    public void mutate(Chromosome chromosome) {

    }
}
