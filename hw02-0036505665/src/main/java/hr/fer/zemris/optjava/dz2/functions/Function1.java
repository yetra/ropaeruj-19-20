package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * An implementation of the function: {@code f1(x, y) = x^2 + (y − 1)^2}.
 *
 * @author Bruna Dujmović
 *
 */
public class Function1 implements IHFunction {

    /**
     * The number of variables of this function.
     */
    private static final int NUMBER_OF_VARIABLES = 2;

    @Override
    public int getNumberOfVariables() {
        return NUMBER_OF_VARIABLES;
    }

    @Override
    public double getValueOf(RealVector point) {
        double[] values = point.toArray();

        return values[0]*values[0] + (values[1] - 1)*(values[1] - 1);
    }

    @Override
    public RealVector getGradientIn(RealVector point) {
        // gradient = [2x 2(y - 1)]^T
        double[] values = point.toArray();
        double[] gradientValues = {2 * values[0], 2 * (values[1] - 1)};

        return new ArrayRealVector(gradientValues);
    }

    @Override
    public RealMatrix getHessianIn(RealVector point) {
        // hessian = [[2 0], [0 2]]
        double[][] hessianValues = {{2, 0}, {0, 2}};

        return new Array2DRowRealMatrix(hessianValues);
    }
}
