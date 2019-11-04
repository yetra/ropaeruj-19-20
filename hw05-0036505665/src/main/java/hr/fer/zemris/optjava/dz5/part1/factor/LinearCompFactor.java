package hr.fer.zemris.optjava.dz5.part1.factor;

/**
 * A comparison factor that changes linearly from 0 to 1 during GA execution.
 *
 * @author Bruna Dujmović
 *
 */
public class LinearCompFactor implements ICompFactor {

    /**
     * The value used to increment the comparison factor in each {@link #getFactor()} call.
     */
    private static final double FACTOR_INCREMENT = 0.01;

    /**
     * The current value of the comparison factor.
     */
    private double factor;

    /**
     * Constructs a {@link LinearCompFactor}.
     */
    public LinearCompFactor(double factor) {
        this.factor = 0.0;
    }

    @Override
    public double getFactor() {
        double currentFactor = factor;
        factor += FACTOR_INCREMENT;

        return currentFactor;
    }
}
