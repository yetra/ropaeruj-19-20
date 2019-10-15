package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

    /**
     * Returns a {@link LinearSystemFunction} parsed from a given file.
     *
     * @param filePath a path to the file to parse
     * @return a {@link LinearSystemFunction} parsed from the given file
     * @throws IOException if an I/O error occurs while reading
     * @throws IllegalArgumentException if the file is incorrectly formatted
     */
    public static LinearSystemFunction fromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        lines.removeIf(line -> line.startsWith("#"));
        int numberOfVariables = lines.size();

        double[][] coefficients = new double[numberOfVariables][numberOfVariables];
        double[] constants = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            String[] parts = lines.get(i).substring(1, lines.get(i).length() - 1).split(", ");

            for (int j = 0; j < parts.length - 1; j++) {
                coefficients[i][j] = Double.parseDouble(parts[j]);
            }
            constants[i] = Double.parseDouble(parts[parts.length - 1]);
        }

        return new LinearSystemFunction(
                new Array2DRowRealMatrix(coefficients), new ArrayRealVector(constants)
        );
    }
}
