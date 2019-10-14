package hr.fer.zemris.optjava.dz2.algorithms;

import hr.fer.zemris.optjava.dz2.Util;
import hr.fer.zemris.optjava.dz2.functions.IFunction;
import org.apache.commons.math3.linear.ArrayRealVector;
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
        RealVector nullVector = new ArrayRealVector(function.getNumberOfVariables());
        RealVector solution = Util.getRandomVector(function.getNumberOfVariables());
        int t = 0;

        while (t < maxTries) {
            if (function.getGradientIn(solution).equals(nullVector)) {
                return solution;
            }

            RealVector d = function.getGradientIn(solution).mapMultiplyToSelf(-1.0);

            double lambda = getLambda(d, solution);

            solution = solution.add(solution.mapMultiplyToSelf(lambda));
            t++;
        }

        return solution;
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
