package hr.fer.zemris.optjava.dz2.algorithms;

import hr.fer.zemris.optjava.dz2.Util;
import hr.fer.zemris.optjava.dz2.functions.IFunction;
import hr.fer.zemris.optjava.dz2.functions.IHFunction;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
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
            System.out.println("Iteration " + t + " - " + solution);
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
    public static RealVector newtonsMethod(IHFunction function, int maxTries) {
        RealVector nullVector = new ArrayRealVector(function.getNumberOfVariables());
        RealVector solution = Util.getRandomVector(function.getNumberOfVariables());
        int t = 0;

        while (t < maxTries) {
            if (function.getGradientIn(solution).equals(nullVector)) {
                return solution;
            }

            RealVector negativeGradient = function.getGradientIn(solution).mapMultiplyToSelf(-1.0);
            RealMatrix inverseHessian = MatrixUtils.inverse(function.getHessianIn(solution));
            RealVector d = inverseHessian.operate(negativeGradient);

            double lambda = getLambda(d, solution);

            solution = solution.add(solution.mapMultiplyToSelf(lambda));
            System.out.println("Iteration " + t + " - " + solution);
            t++;
        }

        return solution;
    }
}
