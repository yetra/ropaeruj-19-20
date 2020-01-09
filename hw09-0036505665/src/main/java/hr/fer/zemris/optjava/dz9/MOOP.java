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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * The path to the results for the decision space distance calculation method.
     */
    private static final Path DECISION_SPACE_PATH = Paths.get("izlaz-dec.txt");

    /**
     * The path to the results for the objective space distance calculation method.
     */
    private static final Path OBJECTIVE_SPACE_PATH = Paths.get("izlaz-obj.txt");

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

        Crossover crossover = new OnePointCrossover(0.98);
        Mutation mutation = new GaussianMutation(0.03, 1, problem.getMins(), problem.getMaxs());
        Selection selection = new RouletteWheelSelection();

        NSGA nsga = new NSGA(
                problem, populationSize, maxIterations, decisionSpaceDistance, crossover, mutation, selection
        );

        List<List<Integer>> fronts = nsga.run();
        print(fronts, nsga.getPopulation(), nsga.getPopulationObjectives(), nsga.getPopulationFitness());

        if (problem instanceof Problem2) {
            if (decisionSpaceDistance) {
                write(DECISION_SPACE_PATH, nsga.getPopulation());
            } else {
                write(OBJECTIVE_SPACE_PATH, nsga.getPopulationObjectives());
            }
        }
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
     * Prints the number of solutions per front, along with the solutions, objectives & fitness for the first front.
     *
     * @param fronts the fronts to print
     * @param population the population of solutions
     * @param populationObjectives the objectives for each solution in the population
     * @param populationFitness the fitness values obtained through fitness sharing for each solution in the population
     */
    private static void print(List<List<Integer>> fronts, double[][] population, double[][] populationObjectives,
                              double[] populationFitness) {
        for (int i = 0, frontsCount = fronts.size(); i < frontsCount; i++) {
            System.out.println("Front " + i + ": " + fronts.get(i).size() + " solutions");
        }

        System.out.println("\nPrinting the first front...");
        List<Integer> firstFront = fronts.get(0);
        for (int i : firstFront) {
            System.out.println("Solution: " + Arrays.toString(population[i])
                    + " => Objectives: " + Arrays.toString(populationObjectives[i])
                    + " | Fitness: " + populationFitness[i]);
        }
    }

    /**
     * Writes the given array to the specified file.
     *
     * @param filePath the path to the file to write
     * @param valuesArray the array to write
     */
    private static void write(Path filePath, double[][] valuesArray) {
        StringBuilder sb = new StringBuilder();

        for (double[] values : valuesArray) {
            for (double value : values) {
                sb.append(value);
                sb.append(",");
            }

            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }

        try {
            Files.writeString(filePath, sb.toString());
        } catch (IOException e) {
            System.out.println("I/O error ocurred!");
            System.exit(1);
        }
    }
}
