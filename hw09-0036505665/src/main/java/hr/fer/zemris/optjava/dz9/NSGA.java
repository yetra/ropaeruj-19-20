package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.dz9.crossover.Crossover;
import hr.fer.zemris.optjava.dz9.mutation.Mutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.selection.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of the Non-Dominated Sorting Genetic Algorithm.
 *
 * This implementation should be used on minimization problems.
 *
 * @author Bruna Dujmović
 *
 */
public class NSGA {

    /**
     * The threshold determining how distant do two solutions need to be to have a share value of zero.
     */
    private static final double SIGMA_SHARE = 1;

    /**
     * The coefficient determining the distribution of the sharing function.
     */
    private static final double SHARE_DISTRIBUTION_COEFFICIENT = 2;

    /**
     * A parameter used for calculating {@link #minFrontFitness}.
     */
    private static final double EPSILON = 0.01;

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
     * {@code true} if the {@link #distance(int, int)} should be calculated in the decision space,
     * {@code false} if it should be calculated in the objective space
     */
    private boolean decisionSpaceDistance;

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
     * The minimum fitness value in a given front.
     */
    private double minFrontFitness;

    /**
     * The population of {@link #problem} solutions.
     */
    private double[][] population;

    /**
     * The objectives for each solution in the {@link #population}.
     */
    private double[][] populationObjectives;

    /**
     * The fitness values obtained through fitness sharing for each solution in the {@link #population}.
     */
    private double[] populationFitness;

    /**
     * The minimum values used when calculating the {@link #distance(int, int)}.
     */
    private double[] distanceMins;

    /**
     * The maximum values used when calculating the {@link #distance(int, int)}.
     */
    private double[] distanceMaxs;

    /**
     * Constructs an instance of {@link NSGA}.
     *
     * @param problem the MOOP problem to minimize
     * @param populationSize the size of the population
     * @param maxIterations the maximum number of algorithm iterations
     * @param decisionSpaceDistance {@code true} if the distance should be calculated in the decision space,
     *                              {@code false} if it should be calculated in the objective space
     * @param crossover the crossover operator to use
     * @param mutation the mutation operator to use
     * @param selection the selection operator to use
     */
    public NSGA(MOOPProblem problem, int populationSize, int maxIterations, boolean decisionSpaceDistance,
                Crossover crossover, Mutation mutation, Selection selection) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.decisionSpaceDistance = decisionSpaceDistance;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;

        minFrontFitness = populationSize * (1 + EPSILON);
        populationFitness = new double[populationSize];
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
     */
    public List<List<Integer>> run() {
        double[][] nextPopulation = new double[populationSize][];

        initialize();

        int iteration = 0;
        while (iteration < maxIterations) {
            List<List<Integer>> fronts = sort();
            evaluate(fronts);

            int childrenCount = 0;
            while (childrenCount < populationSize) {
                double[][] parents = selection.from(population, populationFitness, 2);
                double[][] children = crossover.of(parents[0], parents[1]);
                mutation.mutate(children);

                nextPopulation[childrenCount++] = children[0];
                if (childrenCount == populationSize) {
                    break;
                }
                nextPopulation[childrenCount++] = children[1];
            }

            for (int i = 0; i < populationSize; i++) {
                problem.evaluate(nextPopulation[i], populationObjectives[i]);

                updateDistanceBounds(decisionSpaceDistance ? nextPopulation[i] : populationObjectives[i]);
            }

            population = nextPopulation;
            iteration++;
        }

        List<List<Integer>> fronts = sort();
        evaluate(fronts);

        return fronts;
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

        distanceMins = decisionSpaceDistance ? population[0].clone() : populationObjectives[0].clone();
        distanceMaxs = decisionSpaceDistance ? population[0].clone() : populationObjectives[0].clone();
        for (int i = 1; i < populationSize; i++) {
            updateDistanceBounds(decisionSpaceDistance ? population[i] : populationObjectives[i]);
        }
    }

    /**
     * Returns the fronts obtained by applying non-dominated sorting to the {@link #population}.
     *
     * @return the fronts obtained by applying non-dominated sorting to the {@link #population}
     */
    private List<List<Integer>> sort() {
        List<List<Integer>> dominates = new ArrayList<>(populationSize);
        int[] dominatedBy = new int[populationSize];

        List<Integer> initialFront = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            dominates.add(new ArrayList<>());

            for (int j = 0; j < populationSize; j++) {
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
     * Evaluates the given list of fronts by applying the sharing function algorithm.
     *
     * @param fronts the list of fronts to evaluate
     */
    private void evaluate(List<List<Integer>> fronts) {
        for (List<Integer> front : fronts) {
            double minFitness = Integer.MAX_VALUE;

            for (int solutionIndex : front) {
                populationFitness[solutionIndex] = minFrontFitness * (1 - EPSILON);
                populationFitness[solutionIndex] /= nicheDensity(solutionIndex, front);

                if (populationFitness[solutionIndex] < minFitness) {
                    minFitness = populationFitness[solutionIndex];
                }
            }

            minFrontFitness = minFitness;
        }
    }

    /**
     * Returns the niche density calculated for a solution on the given index,
     * taking into account only the solutions whose indexes are in the specified front.
     *
     * @param i the index of the solution whose density should be calculated
     * @param front the front that specifies which solutions are in the niche
     * @return the niche density calculated for the solution on the given index
     */
    private double nicheDensity(int i, List<Integer> front) {
        double density = 1;

        for (int j : front) {
            if (i == j) {
                continue;
            }

            density += share(distance(i, j));
        }

        return density;
    }

    /**
     * Returns the value of the sharing function at the given distance.
     *
     * @param distance the distance at which the value should be calculated
     * @return the value of the sharing function at the given distance
     */
    private double share(double distance) {
        if (distance >= SIGMA_SHARE) {
            return 0;
        }

        return 1 - Math.pow(distance / SIGMA_SHARE, SHARE_DISTRIBUTION_COEFFICIENT);
    }

    /**
     * Returns the distance between two solutions of the given indexes in the {@link #population}.
     *
     * If {@link #decisionSpaceDistance} is {@code true}, the distance will be calculated in the decision space.
     * Otherwise, it will be calculated in the objective space.
     *
     * @param i the index of the first solution
     * @param j the index of the second solution
     * @return the distance between two solutions of the given indexes in the {@link #population}
     */
    private double distance(int i, int j) {
        double distanceSquared = 0;
        int maxLoops = decisionSpaceDistance ? problem.getNumberOfVariables() : problem.getNumberOfObjectives();

        for (int k = 0; k < maxLoops; k++) {
            if (distanceMins[k] == distanceMaxs[k]) {
                continue;
            }

            double fraction = 1.0 / (distanceMaxs[k] - distanceMins[k]);
            if (decisionSpaceDistance) {
                fraction *= (population[i][k] - population[j][k]);
            } else {
                fraction *= (populationObjectives[i][k] - populationObjectives[j][k]);
            }

            distanceSquared += fraction * fraction;
        }

        return Math.sqrt(distanceSquared);
    }

    /**
     * Updates the {@link #distanceMins} and {@link #distanceMaxs} arrays using the given values array.
     *
     * @param values the values array used for updating - represents a solution if {@link #decisionSpaceDistance}
     *               is {@code true} or a solutions' objectives if {@code false}
     */
    private void updateDistanceBounds(double[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] < distanceMins[i]) {
                distanceMins[i] = values[i];
            } else if (values[i] > distanceMaxs[i]) {
                distanceMaxs[i] = values[i];
            }
        }
    }
}
