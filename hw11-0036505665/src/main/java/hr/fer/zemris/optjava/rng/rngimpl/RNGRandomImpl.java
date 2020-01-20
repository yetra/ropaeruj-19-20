package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;

import java.util.Random;

/**
 * An implementation of {@link IRNG} that uses {@link Random} for random number generation.
 *
 * @author Bruna Dujmović
 *
 */
public class RNGRandomImpl implements IRNG {

    /**
     * The {@link Random} instance to use for random number generation.
     */
    private Random random;

    /**
     * Constructs an {@link RNGRandomImpl} object.
     */
    public RNGRandomImpl() {
        random = new Random();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public float nextFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int min, int max) {
        return random.nextInt() * (max - min) + min;
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return random.nextGaussian();
    }
}
