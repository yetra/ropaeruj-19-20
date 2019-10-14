package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.RealVector;

/**
 * Models an arbitrary function of n real variables.
 *
 * @author Bruna Dujmović
 *
 */
public interface IFunction {

    /**
     * Returns the number of variables of this function.
     *
     * @return the number of variables of this function
     */
    int getNumberOfVariables();

    /**
     * Returns the value of this function in the specified point.
     *
     * @param point the point for calculating the value
     * @return the value of this function in the specified point
     */
    double getValueOf(RealVector point);

    /**
     * Returns the value of the gradient of this function in the specified point.
     *
     * @param point the point for calculating the value of the gradient
     * @return the value of the gradient of this function in the specified point
     */
    RealVector getGradientIn(RealVector point);
}
