package hr.fer.zemris.optjava.dz8.function;

/**
 * Models an arbitrary function of n real variables.
 *
 * @author Bruna Dujmović
 *
 */
public interface Function {

    /**
     * Returns the number of dimensions of this function.
     *
     * @return the number of dimensions of this function
     */
    int getDimensions();

    /**
     * Returns the value of this function at the specified vector.
     *
     * @param vector the vector for calculating the value
     * @return the value of this function in the specified vector
     */
    double valueAt(double[] vector);
}
