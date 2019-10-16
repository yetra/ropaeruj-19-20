package hr.fer.zemris.optjava.dz2.algorithms;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A helper class for creating {@link RealVector} objects.
 *
 * @author Bruna Dujmović
 * 
 */
public class VectorBuilder {

    /**
     * The default minimum value of a randomly generated vector.
     */
    public static final double MIN_VALUE = -5.0;
    /**
     * The default maximum value of a randomly generated vector.
     */
    public static final double MAX_VALUE = 5.0;

    /**
     * Returns a randomly generated vector of the specified size.
     * The values of the vector are in range [{@link #MIN_VALUE}, {@link #MAX_VALUE}].
     *
     * @param size the size of the randomly generated vector
     * @return a randomly generated vector of the specified size
     */
    public static RealVector getRandomVector(int size) {
        return getRandomVectorInRange(size, MIN_VALUE, MAX_VALUE);
    }

    /**
     * Returns a randomly generated vector of the specified size.
     * The values of the vector are in range [{@code minValue}, {@code maxValue}].
     *
     * @param size the size of the randomly generated vector
     * @return a randomly generated vector of the specified size
     */
    public static RealVector getRandomVectorInRange(int size, double minValue, double maxValue) {
        double[] values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = ThreadLocalRandom.current().nextDouble(minValue, maxValue + 1);
        }

        return new ArrayRealVector(values);
    }
}
