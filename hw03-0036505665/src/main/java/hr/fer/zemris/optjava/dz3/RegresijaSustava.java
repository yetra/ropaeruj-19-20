package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.algorithm.annealing.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.annealing.ITempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.annealing.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.decoder.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.function.TransferFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.BitVectorFlipNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhood.DoubleArrayNormNeighborhood;
import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This program uses {@link SimulatedAnnealing} for finding the coefficients of a transfer function
 * whose system response is known.
 *
 * It accepts 2 command-line arguments: the path to a file containing the system response,
 * and the solution type to be used in the algorithm ("decimal" or "binary:number_of_variables").
 *
 * @author Bruna Dujmović
 *
 */
public class RegresijaSustava {

    private static final double[] MINS = {-10, -10, -10, -10, -10, -10};
    private static final double[] MAXS = {10, 10, 10, 10, 10, 10};

    /**
     * The main method. Uses simulated annealing for finding transfer function coefficients.
     *
     * @param args the command-line arguments, 2 expected
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Expected 2 arguments, got " + args.length);
            System.exit(1);
        }

        Path filePath = Paths.get(args[0]);
        String solutionType = args[1];

        TransferFunction function = null;
        try {
            function = TransferFunction.fromFile(filePath);

        } catch (IOException e) {
            System.out.println("An I/O error occured!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("File is incorrectly formatted!");
            System.exit(1);
        }

        int numberOfVariables = function.getNumberOfVariables();
        ITempSchedule tempSchedule = new GeometricTempSchedule(0.99, 1000, 100, 1000);

        if (solutionType.equals("decimal")) {
            DoubleArraySolution solution = new DoubleArraySolution(numberOfVariables);
            solution.randomize(MINS, MAXS);

            new SimulatedAnnealing<>(
                    new PassThroughDecoder(),
                    new DoubleArrayNormNeighborhood(new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1}),
                    solution, function, tempSchedule, true
            ).run();

        } else if (solutionType.startsWith("binary")) {
            int bitsPerVariable = parseBitsPerVariable(solutionType);

            BitVectorSolution solution = new BitVectorSolution(numberOfVariables * bitsPerVariable);
            solution.randomize();

            new SimulatedAnnealing<>(
                    new NaturalBinaryDecoder(MINS, MAXS, bitsPerVariable, numberOfVariables),
                    new BitVectorFlipNeighborhood(), solution, function, tempSchedule, true
            ).run();

        } else {
            System.out.println("Unknown solution type " + solutionType);
            System.exit(1);
        }
    }

    /**
     * Returns the number of bits per variable from the given solution type string.
     *
     * @param solutionType the solution type string, e.g. "binary:10"
     * @return the number of bits per variable from the given solution type string
     */
    private static int parseBitsPerVariable(String solutionType) {
        int bitsPerVariable = 0;

        try {
            bitsPerVariable = Integer.parseInt(solutionType.split(":")[1]);

            if (bitsPerVariable < 5 || bitsPerVariable > 30) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid solution type " + solutionType);
            System.exit(1);
        }

        return bitsPerVariable;
    }
}
