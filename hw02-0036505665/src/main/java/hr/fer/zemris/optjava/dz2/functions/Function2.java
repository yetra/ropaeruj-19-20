package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * An implementation of the function: {@code f2(x, y) = (x - 1)^2 + 10(y − 2)^2}.
 *
 * @author Bruna Dujmović
 *
 */
public class Function2 implements IHFunction {

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

        return (values[0] - 1)*(values[0] - 1) + 10 * (values[1] - 2)*(values[1] - 2);
    }

    @Override
    public RealVector getGradientIn(RealVector point) {
        // gradient = [2(x - 1) 20(y - 2)]^T
        double[] values = point.toArray();
        double[] gradientValues = {2 * (values[0] - 1), 20 * (values[1] - 2)};

        return new ArrayRealVector(gradientValues);
    }

    @Override
    public RealMatrix getHessianIn(RealVector point) {
        // hessian = [[2 0], [0 20]]
        double[][] hessianValues = {{2, 0}, {0, 20}};

        return new Array2DRowRealMatrix(hessianValues);
    }
}
