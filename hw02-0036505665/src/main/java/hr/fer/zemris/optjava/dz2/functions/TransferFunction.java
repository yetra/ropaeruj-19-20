package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * A an implementation of {@link IFunction} for finding the coefficients of a transfer function
 * based on the system's response.
 *
 * The formula of the transfer function with unknown coefficients a, ..., f is:
 * y(x1, x2, x3, x4, x5) = a * x1 + b * x1^3 * x2 + c * exp(d * x3) * (1 + cos(e * x4)) + f * x4 * x5^2
 *
 * @author Bruna Dujmović
 *
 */
public class TransferFunction implements IFunction {

    /**
     * The number of variables of this function.
     */
    private static final int NUMBER_OF_VARIABLES = 6;

    /**
     * A matrix of points.
     */
    private RealMatrix points;

    /**
     * A matrix of function values in the specified {@link #points}.
     */
    private RealVector values;

    /**
     * Constructs a {@link TransferFunction} based on the system's response.
     *
     * @param points a matrix of points
     * @param values a matrix of function values in the specified {@link #points}
     */
    public TransferFunction(RealMatrix points, RealVector values) {
        this.points = points;
        this.values = values;
    }

    @Override
    public int getNumberOfVariables() {
        return NUMBER_OF_VARIABLES;
    }

    @Override
    public double getValueOf(RealVector point) {
        double a = point.getEntry(0);
        double b = point.getEntry(1);
        double c = point.getEntry(2);
        double d = point.getEntry(3);
        double e = point.getEntry(4);
        double f = point.getEntry(5);

        double value = 0.0;

        for (int i = 0, rows = points.getRowDimension(); i < rows; i++) {
            double x1 = points.getEntry(i, 0);
            double x2 = points.getEntry(i, 1);
            double x3 = points.getEntry(i, 2);
            double x4 = points.getEntry(i, 3);
            double x5 = points.getEntry(i, 4);

            double difference = a * x1 + b * x1*x1*x1 * x2
                    + c * Math.exp(d * x3) * (1.0 + Math.cos(e * x4))
                    + f * x4 * x5*x5 - values.getEntry(i);
            value += difference * difference;
        }

        return value;
    }

    @Override
    public RealVector getGradientIn(RealVector point) {
        double a = point.getEntry(0);
        double b = point.getEntry(1);
        double c = point.getEntry(2);
        double d = point.getEntry(3);
        double e = point.getEntry(4);
        double f = point.getEntry(5);

        double[] gradientValues = new double[NUMBER_OF_VARIABLES];

        for (int i = 0, rows = points.getRowDimension(); i < rows; i++) {
            double x1 = points.getEntry(i, 0);
            double x2 = points.getEntry(i, 1);
            double x3 = points.getEntry(i, 2);
            double x4 = points.getEntry(i, 3);
            double x5 = points.getEntry(i, 4);

            double doubledDifference = 2 * (a * x1 + b * x1*x1*x1 * x2
                    + c * Math.exp(d * x3) * (1.0 + Math.cos(e * x4))
                    + f * x4 * x5*x5 - values.getEntry(i));

            gradientValues[0] += doubledDifference * x1;
            gradientValues[1] += doubledDifference * x1*x1*x1 * x2;
            gradientValues[2] += doubledDifference * Math.exp(d * x3) * (1.0 + Math.cos(e * x4));
            gradientValues[3] += doubledDifference * x3 * c * Math.exp(d * x3) * (1.0 + Math.cos(e * x4));
            gradientValues[4] -= doubledDifference * c * Math.exp(d * x3) * Math.sin(e * x4) * x4;
            gradientValues[5] += doubledDifference * x4 * x5*x5;
        }

        return new ArrayRealVector(gradientValues);
    }
}
