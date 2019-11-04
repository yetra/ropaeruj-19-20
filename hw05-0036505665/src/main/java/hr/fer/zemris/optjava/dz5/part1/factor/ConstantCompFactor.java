package hr.fer.zemris.optjava.dz5.part1.factor;

/**
 * A comparison factor that remains constant throughout the genetic algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class ConstantCompFactor implements ICompFactor {

    /**
     * The constant value of the comparison factor.
     */
    private double factor;

    /**
     * Constructs a {@link ConstantCompFactor} of the given value.
     *
     * @param factor the comparison factor (double between 0 and 1)
     */
    public ConstantCompFactor(double factor) {
        this.factor = factor;
    }

    @Override
    public double getFactor() {
        return factor;
    }
}
