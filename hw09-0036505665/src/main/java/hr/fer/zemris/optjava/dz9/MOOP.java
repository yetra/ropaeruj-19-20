package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.dz9.crossover.Crossover;
import hr.fer.zemris.optjava.dz9.crossover.OnePointCrossover;
import hr.fer.zemris.optjava.dz9.mutation.GaussianMutation;
import hr.fer.zemris.optjava.dz9.mutation.Mutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.problem.Problem1;
import hr.fer.zemris.optjava.dz9.problem.Problem2;
import hr.fer.zemris.optjava.dz9.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz9.selection.Selection;

/**
 * A program for running the Non-Dominated Sorting Genetic Algorithm (NSGA) on a specified {@link MOOPProblem}.
 *
 * Usage examples:
 * 2 100 decision-space 1000
 * 1 100 objective-space 2500
 *
 * @author Bruna Dujmović
 *
 */
public class MOOP {

    /**
     * Executes the NSGA on the specified optimization problem.
     *
     * @param args the command-line arguments, 4 expected:
     *             problemNumber - the number of the MOOP problem to use (1 / 2),
     *             populationSize - the size of the population,
     *             distanceCalculationString - the distance calculation method (decision-space / objective-space),
     *             maxIterations - the maximum number of algorithm iterations
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Expected 4 arguments, got " + args.length);
            System.exit(1);
        }

        MOOPProblem problem = getProblem(args[0]);
        int populationSize = Integer.parseInt(args[1]);
        boolean decisionSpaceDistance = getDistanceCalculationType(args[2]);
        int maxIterations = Integer.parseInt(args[3]);

        Crossover crossover = new OnePointCrossover(0.9);
        Mutation mutation = new GaussianMutation(0.03, 1);
        Selection selection = new RouletteWheelSelection();

        new NSGA(problem, populationSize, maxIterations, decisionSpaceDistance, crossover, mutation, selection).run();
    }

    private static MOOPProblem getProblem(String problemNumber) {
        return null;
    }

    /**
     * Returns {@code true} if the solution distance should be calculated in the decision space,
     * {@code false} if it should be calculated in the objective space.
     *
     * @param distanceCalculationString a string specifying the distance calculation method
     *                                  (decision-space / objective-space)
     * @return {@code true} if the solution distance should be calculated in the decision space,
     *         {@code false} if it should be calculated in the objective space
     */
    private static boolean getDistanceCalculationType(String distanceCalculationString) {
        switch (distanceCalculationString) {
            case "decision-space":
                return true;
            case "objective-space":
                return false;
            default:
                throw new IllegalArgumentException(
                        "Unknown distance calculation string " + distanceCalculationString
                );
        }
    }
}
