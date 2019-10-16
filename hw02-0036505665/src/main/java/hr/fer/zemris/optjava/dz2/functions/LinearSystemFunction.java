package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * A an implementation of {@link IHFunction} for solving a system of linear equations using numerical
 * optimization methods.
 *
 * Solving the linear equation Ax - b = 0 can be seen as minimizing the function F(x) = ||Ax - b||^2,
 * where A is a matrix of coefficients, x is a vector of unknown variables, and b is a vector of constants.
 *
 * @author Bruna Dujmović
 *
 */
public class LinearSystemFunction implements IHFunction {

    /**
     * A matrix of linear system coefficients.
     */
    private RealMatrix coefficients;

    /**
     * A vector of linear system constants.
     */
    private RealVector constants;

    /**
     * Constructs a {@link LinearSystemFunction} using the coefficients and constants of a given linear system.
     *
     * @param coefficients a matrix of linear system coefficients
     * @param constants a vector of linear system constants
     */
    public LinearSystemFunction(RealMatrix coefficients, RealVector constants) {
        this.coefficients = coefficients;
        this.constants = constants;
    }

    @Override
    public int getNumberOfVariables() {
        return coefficients.getColumnDimension();
    }

    @Override
    public double getValueOf(RealVector point) {
        // F(x) = ||A*x - b||^2
        double norm = coefficients.operate(point).subtract(constants).getNorm();

        return norm * norm;
    }

    @Override
    public RealVector getGradientIn(RealVector point) {
        // gradient F(x) = 2*A^T * (A*x - b)
        return coefficients.transpose().scalarMultiply(2.0)
                .operate(coefficients.operate(point).subtract(constants));
    }

    @Override
    public RealMatrix getHessianIn(RealVector point) {
        // hessian F(x) = gradient of gradient F(x) = 2 * A^T * A
        return coefficients.transpose().scalarMultiply(2.0).multiply(coefficients);
    }
}
