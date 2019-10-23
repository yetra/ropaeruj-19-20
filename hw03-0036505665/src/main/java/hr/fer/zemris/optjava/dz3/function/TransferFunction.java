package hr.fer.zemris.optjava.dz3.function;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
     * The number of unknown coefficients (a, ..., f).
     */
    private static final int NUMBER_OF_VARIABLES = 6;

    /**
     * The original number of variables (x1, ..., x5) of the transfer function.
     */
    private static final int NUMBER_OF_ORIG_VARIABLES = 5;

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

    public int getNumberOfVariables() {
        return NUMBER_OF_VARIABLES;
    }

    @Override
    public double valueAt(double[] point) {
        RealVector pointVector = new ArrayRealVector(point);
        double a = pointVector.getEntry(0);
        double b = pointVector.getEntry(1);
        double c = pointVector.getEntry(2);
        double d = pointVector.getEntry(3);
        double e = pointVector.getEntry(4);
        double f = pointVector.getEntry(5);

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

    /**
     * Returns a {@link TransferFunction} parsed from the given file.
     *
     * @param filePath a path to the file to parse
     * @return a {@link TransferFunction} parsed from the given file
     * @throws IOException if an I/O error occurs while reading
     * @throws IllegalArgumentException if the file is incorrectly formatted
     */
    public static TransferFunction fromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        lines.removeIf(line -> line.startsWith("#"));
        int lineCount = lines.size();

        double[][] xes = new double[lineCount][NUMBER_OF_ORIG_VARIABLES];
        double[] ys = new double[lineCount];

        for (int i = 0; i < lineCount; i++) {
            String[] parts = lines.get(i).substring(1, lines.get(i).length() - 1).split(", ");

            for (int j = 0; j < parts.length - 1; j++) {
                xes[i][j] = Double.parseDouble(parts[j]);
            }
            ys[i] = Double.parseDouble(parts[parts.length - 1]);
        }

        return new TransferFunction(new Array2DRowRealMatrix(xes), new ArrayRealVector(ys));
    }
}
