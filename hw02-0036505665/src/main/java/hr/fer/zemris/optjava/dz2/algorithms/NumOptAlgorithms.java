package hr.fer.zemris.optjava.dz2.algorithms;

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
     * A constant used for comparing doubles to zero.
     */
    private static final double PRECISION = 1E-3;

    /**
     * Implements the gradient descent algorithm for finding the minimum of a given function.
     *
     * @param function the function whose minimum should be found
     * @param maxTries the maximum number of iterations before the algorithm terminates
     * @return the approximate minimum of the given function
     */
    public static RealVector gradientDescent(IFunction function, int maxTries) {
        RealVector nullVector = new ArrayRealVector(function.getNumberOfVariables());
        RealVector solution = VectorBuilder.getRandomVector(function.getNumberOfVariables());
        int t = 0;

        while (t < maxTries) {
            if (function.getGradientIn(solution).equals(nullVector)) {
                return solution;
            }

            RealVector d = function.getGradientIn(solution).mapMultiplyToSelf(-1.0);

            double lambda = getLambda(function, d, solution);

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
        RealVector solution = VectorBuilder.getRandomVector(function.getNumberOfVariables());
        int t = 0;

        while (t < maxTries) {
            if (function.getGradientIn(solution).equals(nullVector)) {
                return solution;
            }

            RealVector negativeGradient = function.getGradientIn(solution).mapMultiplyToSelf(-1.0);
            RealMatrix inverseHessian = MatrixUtils.inverse(function.getHessianIn(solution));
            RealVector d = inverseHessian.operate(negativeGradient);

            double lambda = getLambda(function, d, solution);

            solution = solution.add(solution.mapMultiplyToSelf(lambda));
            System.out.println("Iteration " + t + " - " + solution);
            t++;
        }

        return solution;
    }

    /**
     * Returns the lambda parameter calculated using the bisection method.
     *
     * @param function the function that is being minimized
     * @param d a vector in the direction of the change needed to decrease the function's values
     * @param solution the current solution
     * @return the lambda parameter calculated using the bisection method
     */
    private static double getLambda(IFunction function, RealVector d, RealVector solution) {
        double lower = 0;
        double upper = getLambdaUpper(function, d, solution);

        double lambda;
        while (true) {
            lambda = (lower + upper) / 2;

            RealVector x = solution.add(d.mapMultiplyToSelf(lambda));
            double thetaDerivative = function.getGradientIn(x).dotProduct(d);

            if (Math.abs(thetaDerivative) < PRECISION) {
                break;
            }

            if (thetaDerivative > 0) {
                upper = lambda;
            } else {
                lower = lambda;
            }
        }

        return lambda;
    }

    /**
     * Returns the upper bound of the range in which the lambda parameter lies.
     *
     * @param function the function that is being minimized
     * @param d a vector in the direction of the change needed to decrease the function's values
     * @param solution the current solution
     * @return the upper bound of the range in which the lambda parameter lies
     */
    private static double getLambdaUpper(IFunction function, RealVector d, RealVector solution) {
        double upper = 1;

        while (true) {
            RealVector x = solution.add(d.mapMultiplyToSelf(upper));
            double thetaDerivative = function.getGradientIn(x).dotProduct(d);

            if (thetaDerivative > 0) {
                break;
            } else {
                upper *= 2;
            }
        }

        return upper;
    }
}
