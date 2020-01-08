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

import java.util.Arrays;
import java.util.List;

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
        Mutation mutation = new GaussianMutation(0.03, 1, problem.getMins(), problem.getMaxs());
        Selection selection = new RouletteWheelSelection();

        NSGA nsga = new NSGA(
                problem, populationSize, maxIterations, decisionSpaceDistance, crossover, mutation, selection
        );

        List<List<Integer>> fronts = nsga.run();
        print(fronts, nsga.getPopulation(), nsga.getPopulationObjectives());
    }

    /**
     * Returns a {@link MOOPProblem} instance parsed from the given string.
     *
     * @param problemNumber the number of the MOOP problem to use (1 / 2)
     * @return a {@link MOOPProblem} instance parsed from the given string
     */
    private static MOOPProblem getProblem(String problemNumber) {
        switch (problemNumber) {
            case "1":
                return new Problem1();
            case "2":
                return new Problem2();
            default:
                throw new IllegalArgumentException("Unknown problem number " + problemNumber);
        }
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

    /**
     * Prints the number of solutions per front, along with the solutions & objectives for the first front.
     *
     * @param fronts the fronts to print
     * @param population the population of solutions
     * @param populationObjectives the objectives for each solution in the population
     */
    private static void print(List<List<Integer>> fronts, double[][] population, double[][] populationObjectives) {
        for (int i = 0, frontsCount = fronts.size(); i < frontsCount; i++) {
            System.out.println("Front " + i + ": " + fronts.get(i).size() + " solutions");
        }

        System.out.println("\nPrinting the first front...");
        List<Integer> firstFront = fronts.get(0);
        for (int i : firstFront) {
            System.out.println("Solution: " + Arrays.toString(population[i])
                    + " => Objectives: " + Arrays.toString(populationObjectives[i]));
        }
    }
}
