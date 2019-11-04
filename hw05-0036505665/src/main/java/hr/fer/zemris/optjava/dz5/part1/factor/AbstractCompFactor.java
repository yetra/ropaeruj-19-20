package hr.fer.zemris.optjava.dz5.part1.factor;

/**
 * An abstract class to be implemented by various comparison factor change plans.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class AbstractCompFactor {

    /**
     * The current comparison factor.
     */
    double factor;

    /**
     * Constructs a comparison factor object of the given initial value.
     *
     * @param factor the initial value of the comparison factor (double between 0 and 1)
     */
    public AbstractCompFactor(double factor) {
        this.factor = factor;
    }

    /**
     * Returns the current comparison factor.
     *
     * @return the current comparison factor
     */
    public double getFactor() {
        return factor;
    }
}
