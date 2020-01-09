package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.selection.Selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of NSGA-II.
 *
 * This implementation should be used only on minimization problems.
 *
 * @author Bruna Dujmović
 *
 */
public class NSGA2 {

    /**
     * The MOOP problem to minimize.
     */
    private MOOPProblem problem;

    /**
     * The size of the population.
     */
    private int populationSize;

    /**
     * The maximum number of algorithm iterations.
     */
    private int maxIterations;

    /**
     * The crossover operator to use.
     */
    private Crossover crossover;

    /**
     * The mutation operator to use.
     */
    private Mutation mutation;

    /**
     * The selection operator to use.
     */
    private Selection selection;

    /**
     * The population of {@link #problem} solutions.
     */
    private double[][] population;

    /**
     * The objectives for each solution in the {@link #population}.
     */
    private double[][] populationObjectives;

    /**
     * The crowding distances for each solution in the population.
     */
    private double[] crowdingDistance; // TODO init

    /**
     * Constructs an instance of {@link NSGA2}.
     *
     * @param problem the MOOP problem to minimize
     * @param populationSize the size of the population
     * @param maxIterations the maximum number of algorithm iterations
     * @param crossover the crossover operator to use
     * @param mutation the mutation operator to use
     * @param selection the selection operator to use
     */
    public NSGA2(MOOPProblem problem, int populationSize, int maxIterations,
                 Crossover crossover, Mutation mutation, Selection selection) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
    }

    /**
     * Returns the population of {@link #problem} solutions.
     *
     * @return the population of {@link #problem} solutions
     */
    public double[][] getPopulation() {
        return population;
    }

    /**
     * Returns the objectives for each solution in the {@link #population}.
     *
     * @return the objectives for each solution in the {@link #population}
     */
    public double[][] getPopulationObjectives() {
        return populationObjectives;
    }

    /**
     * Executes the algorithm.
     *
     * @return the fronts belonging to the last population
     */
    public List<List<Integer>> run() {
        initialize();

        List<List<Integer>> fronts = nonDominatedSort(population);

        double[][] childPopulation = new double[populationSize][];
        double[][] nextPopulation = new double[populationSize][];

        double[][] union = new double[populationSize * 2][];
        double[][] unionObjectives = new double[populationSize * 2][problem.getNumberOfObjectives()];

        int iteration = 0;
        while (iteration < maxIterations) {
            // generiraj populaciju djece

            System.arraycopy(population, 0, union, 0, populationSize);
            System.arraycopy(childPopulation, 0, union, populationSize, populationSize);
            // TODO child objectives

            fronts = nonDominatedSort(union);

            int added = 0;
            int frontIndex = 0;
            while (fronts.get(frontIndex).size() + added < populationSize) {
                for (int i : fronts.get(frontIndex)) {
                    nextPopulation[added++] = union[i];
                }
                frontIndex++;
            }

            List<Integer> tooLargeFront = fronts.get(frontIndex);
            crowdingSort(tooLargeFront, union, unionObjectives);

            tooLargeFront.sort(Collections.reverseOrder(Comparator.comparingDouble(index -> crowdingDistance[index])));

            for (int i : tooLargeFront) {
                nextPopulation[added++] = union[i];

                if (nextPopulation.length == populationSize) {
                    break;
                }
            }

            // sljedece fronte = fronte u populaciji + podskup iz fronte koja ne stane (zadnja fronta)

            population = nextPopulation;
            iteration++;
        }

        return null;
    }

    /**
     * Initializes the {@link #population} and {@link #populationObjectives} arrays.
     */
    private void initialize() {
        population = new double[populationSize][problem.getNumberOfVariables()];
        populationObjectives = new double[populationSize][problem.getNumberOfObjectives()];

        double[] mins = problem.getMins();
        double[] maxs = problem.getMaxs();

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0, solutionSize = problem.getNumberOfVariables(); j < solutionSize; j++) {
                population[i][j] = ThreadLocalRandom.current().nextDouble(mins[j], maxs[j]);
            }

            problem.evaluate(population[i], populationObjectives[i]);
        }
    }

    /**
     * Returns the fronts obtained by performing a non-dominated sort of the given population.
     *
     * @param population the population to sort
     * @return the fronts obtained by performing a non-dominated sort of the given population
     */
    private List<List<Integer>> nonDominatedSort(double[][] population) {
        List<List<Integer>> dominates = new ArrayList<>(population.length);
        int[] dominatedBy = new int[population.length];

        List<Integer> initialFront = new ArrayList<>();
        for (int i = 0; i < population.length; i++) {
            dominates.add(new ArrayList<>());

            for (int j = 0; j < population.length; j++) {
                if (i == j) {
                    continue;
                }

                if (dominates(i, j)) {
                    dominates.get(i).add(j);
                } else if (dominates(j, i)) {
                    dominatedBy[i]++;
                }
            }

            if (dominatedBy[i] == 0) {
                initialFront.add(i);
            }
        }

        List<List<Integer>> fronts = new ArrayList<>();
        List<Integer> currentFront = initialFront;
        while (!currentFront.isEmpty()) {
            fronts.add(currentFront);
            List<Integer> nextFront = new ArrayList<>();

            for (int i : currentFront) {
                for (int j : dominates.get(i)) {
                    dominatedBy[j]--;

                    if (dominatedBy[j] == 0) {
                        nextFront.add(j);
                    }
                }
            }

            currentFront = nextFront;
        }

        return fronts;
    }

    /**
     * Returns {@code true} if the solution on the {@code firstIndex} dominates
     * the solution on the {@code secondIndex}.
     *
     * @param firstIndex the index of the first solution
     * @param secondIndex the index of the second solution
     * @return {@code true} if the first solution dominates the second solution
     */
    private boolean dominates(int firstIndex, int secondIndex) {
        double[] firstObjectives = populationObjectives[firstIndex];
        double[] secondObjectives = populationObjectives[secondIndex];
        boolean isStrictlyBetter = false;

        for (int i = 0; i < firstObjectives.length; i++) {
            if (firstObjectives[i] > secondObjectives[i]) {
                return false;
            } else if (firstObjectives[i] < secondObjectives[i]) {
                isStrictlyBetter = true;
            }
        }

        return isStrictlyBetter;
    }

    /**
     * Applies the crowding sort algorithm to solutions in the given front.
     *
     * @param front the front determining which solutions should be sorted
     * @param population the population containing the solutions
     * @param populationObjectives the objectives for each solution in the population
     */
    private void crowdingSort(List<Integer> front, double[][] population, double[][] populationObjectives) {
        for (int i = 0; i < populationObjectives[0].length; i++) {
            int finalI = i;
            front.sort(Comparator.comparingDouble(integer -> populationObjectives[integer][finalI]));

            crowdingDistance[front.get(0)] = Double.POSITIVE_INFINITY;
            crowdingDistance[front.get(front.size() - 1)] = Double.POSITIVE_INFINITY;

            for (int j = 1, frontSize = front.size(); j < frontSize - 1; j++) {
                int index = front.get(j);

                crowdingDistance[index] += (populationObjectives[index + 1][i] - populationObjectives[index - 1][i]);
                // TODO fmax - fmin
            }
        }
    }
}
