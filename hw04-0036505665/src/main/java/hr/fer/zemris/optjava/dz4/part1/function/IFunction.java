package hr.fer.zemris.optjava.dz4.part1.function;

/**
 * Models an arbitrary function of n real variables.
 *
 * @author Bruna Dujmović
 *
 */
public interface IFunction {

    /**
     * Returns the value of this function at the specified point.
     *
     * @param point the point for calculating the value
     * @return the value of this function in the specified point
     */
    double valueAt(double[] point);
}
