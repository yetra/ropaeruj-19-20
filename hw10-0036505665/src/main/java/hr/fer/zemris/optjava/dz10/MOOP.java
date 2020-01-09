package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.crossover.ArithmeticCrossover;
import hr.fer.zemris.optjava.dz10.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.mutation.GaussianMutation;
import hr.fer.zemris.optjava.dz10.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.problem.Problem1;
import hr.fer.zemris.optjava.dz10.problem.Problem2;
import hr.fer.zemris.optjava.dz10.selection.TournamentSelection;
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
        Selection selection = new TournamentSelection(2);

        NSGA2 nsga2 = new NSGA2(problem, populationSize, maxIterations, crossover, mutation, selection);

        List<List<Solution>> fronts = nsga2.run();
        print(fronts);

        write(DECISION_SPACE_PATH, nsga2.getPopulation(), true);
        write(OBJECTIVE_SPACE_PATH, nsga2.getPopulation(), false);
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
     * Prints the first front, and the number of solutions per front.
     *
     * @param fronts the fronts to print
     */
    private static void print(List<List<Solution>> fronts) {
        for (int i = 0, frontsCount = fronts.size(); i < frontsCount; i++) {
            System.out.println("Front " + i + ": " + fronts.get(i).size() + " solutions");
        }

        System.out.println("\nPrinting the first front...");
        List<Solution> firstFront = fronts.get(0);
        for (Solution solution : firstFront) {
            System.out.println("Solution: " + Arrays.toString(solution.variables)
                    + " => Objectives: " + Arrays.toString(solution.objectives));
        }
    }

    /**
     * Writes a given population's variables of objectives to the specified file.
     *
     * @param filePath the path to the file to write to
     * @param population the population of solutions
     * @param decisionSpace {@code true} when writing variables, {@code false} when writing objectives
     */
    private static void write(Path filePath, Solution[] population, boolean decisionSpace) {
        StringBuilder sb = new StringBuilder();

        for (Solution solution : population) {
            double[] values = decisionSpace ? solution.variables : solution.objectives;

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
