package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Models an arbitrary function of n real variables whose Hesse matrix can be calculated in a given point.
 *
 * @see IFunction
 * @author Bruna Dujmović
 *
 */
public interface IHFunction extends IFunction {

    /**
     * Returns the Hessian matrix of this function in the given point.
     *
     * @param point the point for calculating the Hessian matrix
     * @return the Hessian matrix of this function in the given point
     */
    RealMatrix getHessianIn(RealVector point);
}
