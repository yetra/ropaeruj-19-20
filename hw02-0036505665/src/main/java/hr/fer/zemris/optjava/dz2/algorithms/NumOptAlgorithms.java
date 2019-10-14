package hr.fer.zemris.optjava.dz2.algorithms;

import hr.fer.zemris.optjava.dz2.functions.IFunction;
import org.apache.commons.math3.linear.RealVector;

/**
 * This class contains the implementations of numerical optimization algorithmns used for finding a function's minimum.
 *
 * @author Bruna Dujmović
 *
 */
public class NumOptAlgorithms {

    /**
     * Implements the gradient descent algorithm for finding the minimum of a given function.
     *
     * @param function the function whose minimum should be found
     * @param maxTries the maximum number of iterations before the algorithm terminates
     * @return the approximate minimum of the given function
     */
    public static RealVector gradientDescent(IFunction function, int maxTries) {
        return null;
    }

    /**
     * Implements Newton's method for finding the minimum of a given function.
     *
     * @param function the function whose minimum should be found
     * @param maxTries the maximum number of iterations before the algorithm terminates
     * @return the approximate minimum of the given function
     */
    public static RealVector newtonsMethod(IFunction function, int maxTries) {
        return null;
    }
}
