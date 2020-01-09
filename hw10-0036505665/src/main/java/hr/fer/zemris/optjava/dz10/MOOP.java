package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.crossover.ArithmeticCrossover;
import hr.fer.zemris.optjava.dz10.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.mutation.GaussianMutation;
import hr.fer.zemris.optjava.dz10.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.problem.Problem1;
import hr.fer.zemris.optjava.dz10.problem.Problem2;
import hr.fer.zemris.optjava.dz10.selection.CrowdedTournamentSelection;
import hr.fer.zemris.optjava.dz10.selection.Selection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * A program for running the Non-Dominated Sorting Genetic Algorithm (NSGA) on a specified {@link MOOPProblem}.
 *
 * Usage examples:
 * 2 100 1000
 * 1 50 2500
 *
 * @author Bruna Dujmović
 *
 */
public class MOOP {

    /**
     * The path to the results for the decision space.
     */
    private static final Path DECISION_SPACE_PATH = Paths.get("izlaz-dec.txt");

    /**
     * The path to the results for the objective space.
     */
    private static final Path OBJECTIVE_SPACE_PATH = Paths.get("izlaz-obj.txt");

    /**
     * Executes the NSGA on the specified optimization problem.
     *
     * @param args the command-line arguments, 4 expected:
     *             problemNumber - the number of the MOOP problem to use (1 / 2),
     *             populationSize - the size of the population,
     *             maxIterations - the maximum number of algorithm iterations
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Expected 3 arguments, got " + args.length);
            System.exit(1);
        }

        MOOPProblem problem = getProblem(args[0]);
        int populationSize = Integer.parseInt(args[1]);
        int maxIterations = Integer.parseInt(args[2]);

        Crossover crossover = new ArithmeticCrossover(0.5, problem.getMins(), problem.getMaxs());
        Mutation mutation = new GaussianMutation(0.03, 1, problem.getMins(), problem.getMaxs());
        Selection selection = new CrowdedTournamentSelection();

        NSGA2 nsga = new NSGA2(
                problem, populationSize, maxIterations, crossover, mutation, selection
        );

        List<List<Integer>> fronts = nsga.run();
        print(fronts, nsga.getPopulation(), nsga.getPopulationObjectives());

        write(DECISION_SPACE_PATH, nsga.getPopulation());
        write(OBJECTIVE_SPACE_PATH, nsga.getPopulationObjectives());
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
     * Prints the number of solutions per front, along with the solutions, objectives & fitness for the first front.
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
