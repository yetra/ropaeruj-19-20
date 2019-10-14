package hr.fer.zemris.optjava.dz2;

import hr.fer.zemris.optjava.dz2.algorithms.NumOptAlgorithms;
import hr.fer.zemris.optjava.dz2.functions.Function1;
import hr.fer.zemris.optjava.dz2.functions.Function2;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * This program demonstrates the implemented {@link NumOptAlgorithms} on {@link Function1} and {@link Function2}.
 * It accepts 2 or 4 command-line arguments:
 *     1. task name - {"1a", "1b", "2a", "2b"}
 *     2. the maximum number of iterations
 *    [3. the x-coordinate of the initial solution
 *     4. the y-coordinate of the initial solution]
 *
 * Results are printed to the console.
 *
 * @author Bruna Dujmović
 *
 */
public class Jednostavno {

    /**
     * The main method. Demonstrates the {@link NumOptAlgorithms}.
     *
     * @param args the command-line arguments, 2 or 4 expected
     */
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 4) {
            System.out.println("Expected 2 or 4 arguments, got " + args.length);
            System.exit(1);
        }

        String taskName = args[0];
        int maxTries = Integer.parseInt(args[1]);

        // TODO catch NumberFormatException?
        // TODO use initial solution
        RealVector initialSolution = null;
        if (args.length == 4) {
            double[] values = {Double.parseDouble(args[2]), Double.parseDouble(args[3])};
            initialSolution = new ArrayRealVector(values);
        }

        RealVector solution = null;
        switch (taskName) {
            case "1a":
                solution = NumOptAlgorithms.gradientDescent(new Function1(), maxTries);
                break;

            case "1b":
                solution = NumOptAlgorithms.newtonsMethod(new Function1(), maxTries);
                break;

            case "2a":
                solution = NumOptAlgorithms.gradientDescent(new Function2(), maxTries);
                break;

            case "2b":
                solution = NumOptAlgorithms.newtonsMethod(new Function2(), maxTries);
                break;

            default:
                System.out.println("Unknown task " + taskName);
                System.exit(1);
        }

        System.out.println("\nSolution: " + solution);
    }
}
