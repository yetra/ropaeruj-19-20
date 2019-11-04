package hr.fer.zemris.optjava.dz5.part1;

/**
 * A comparison factor that remains constant throughout the genetic algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class ConstantCompFactor extends AbstractCompFactor {

    /**
     * Constructs a {@link ConstantCompFactor} of the given value.
     *
     * @param factor the comparison factor (double between 0 and 1)
     */
    public ConstantCompFactor(double factor) {
        super(factor);
    }
}
