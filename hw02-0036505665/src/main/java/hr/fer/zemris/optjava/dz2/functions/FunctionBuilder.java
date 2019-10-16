package hr.fer.zemris.optjava.dz2.functions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A helper class for creating {@link IFunction} objects.
 *
 * @author Bruna Dujmović
 *
 */
public class FunctionBuilder {

    /**
     * An enum of {@link IFunction} types that can be parsed from a file.
     */
    public enum FunctionType {
        LINEAR_SYSTEM,
        TRANSFER
    }

    /**
     * Returns an {@link IFunction} parsed from a given file.
     *
     * @param type the type of the function being parsed
     * @param filePath a path to the file to parse
     * @return an {@link IFunction} parsed from the given file
     * @throws IOException if an I/O error occurs while reading
     * @throws IllegalArgumentException if the file is incorrectly formatted
     */
    public static IFunction fromFile(FunctionType type, int numberOfVariables, Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        lines.removeIf(line -> line.startsWith("#"));

        double[][] xes = new double[lines.size()][numberOfVariables];
        double[] ys = new double[lines.size()];

        for (int i = 0; i < numberOfVariables; i++) {
            String[] parts = lines.get(i).substring(1, lines.get(i).length() - 1).split(", ");

            for (int j = 0; j < parts.length - 1; j++) {
                xes[i][j] = Double.parseDouble(parts[j]);
            }
            ys[i] = Double.parseDouble(parts[parts.length - 1]);
        }

        if (type == FunctionType.LINEAR_SYSTEM) {
            return new LinearSystemFunction(new Array2DRowRealMatrix(xes), new ArrayRealVector(ys));
        } else {
            return new TransferFunction(new Array2DRowRealMatrix(xes), new ArrayRealVector(ys));
        }
    }
}
