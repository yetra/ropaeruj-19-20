package hr.fer.zemris.optjava.dz2;

import hr.fer.zemris.optjava.dz2.algorithms.NumOptAlgorithms;
import hr.fer.zemris.optjava.dz2.functions.FunctionBuilder;
import hr.fer.zemris.optjava.dz2.functions.FunctionBuilder.FunctionType;
import hr.fer.zemris.optjava.dz2.functions.TransferFunction;
import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This program uses the implemented {@link NumOptAlgorithms} for finding the coefficients of a transfer function
 * whose system response is known.
 *
 * It accepts 3 command-line arguments: algorithm name ("grad" or "newton"), the maximum number of iterations,
 * and the path to a file containing the system response.
 *
 * The results are printed to the console.
 *
 * @author Bruna Dujmović
 *
 */
public class Prijenosna {

    /**
     * The main method. Uses {@link NumOptAlgorithms} for finding transfer function coefficients.
     *
     * @param args the command-line arguments, 3 expected
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Expected 3 arguments, got " + args.length);
            System.exit(1);
        }

        int maxTries = Integer.parseInt(args[1]);
        Path filePath = Paths.get(args[2]);

        TransferFunction function = null;
        try {
            function = (TransferFunction) FunctionBuilder.fromFile(FunctionType.TRANSFER, 5, filePath);

        } catch (IOException e) {
            System.out.println("An I/O error occured!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("File is incorrectly formatted!");
            System.exit(1);
        }

        RealVector solution = null;
        switch (args[0]) {
            case "grad":
                solution = NumOptAlgorithms.gradientDescent(function, maxTries, null);
                break;
            case "newton":
                System.out.println("Unsupported algorithm for finding coefficients! Try gradient descent.");
                System.exit(1);
                break;
            default:
                System.out.println("Unknown algorithm name " + args[0]);
                System.exit(1);
        }

        System.out.println("\nSolution: " + solution);
        System.out.println("Error value: " + function.getValueOf(solution));
    }
}
