package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.selection.Selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private Solution[] population;

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
    public Solution[] getPopulation() {
        return population;
    }

    /**
     * Executes the algorithm.
     *
     * @return the fronts belonging to the last population
     */
    public List<List<Solution>> run() {
        initialize();

        List<List<Solution>> fronts = buildFronts(population);

        Solution[] nextPopulation = new Solution[populationSize];
        Solution[] union = new Solution[populationSize * 2];

        int iteration = 0;
        while (iteration < maxIterations) {
            populate(union);

            fronts = buildFronts(union);

            // copy entire fronts and find the first one that doesn't fit
            int added = 0;
            List<Solution> tooLargeFront = null;
            for (List<Solution> front : fronts) {
                if (front.size() + added >= populationSize) {
                    tooLargeFront = front;
                    break;
                }

                for (Solution solution: front) {
                    nextPopulation[added++] = solution;
                }
            }

            // sort the too large front by crowding distance
            assert tooLargeFront != null;
            tooLargeFront.sort(Comparator.comparingDouble((Solution s) -> s.crowdingDistance).reversed());

            // fill next population with the best solutions in the too large front
            List<Solution> newLastFront = new ArrayList<>();
            for (Solution solution : tooLargeFront) {
                nextPopulation[added++] = solution;
                newLastFront.add(solution);

                if (nextPopulation.length == populationSize) {
                    break;
                }
            }

            // update fronts
            fronts = fronts.subList(0, fronts.indexOf(tooLargeFront));
            fronts.add(newLastFront);

            population = nextPopulation;
            iteration++;
        }

        return fronts;
    }

    /**
     * Initializes the {@link #population}.
     */
    private void initialize() {
        population = new Solution[populationSize];

        int numberOfVariables = problem.getNumberOfVariables();
        int numberOfObjectives = problem.getNumberOfObjectives();

        double[] mins = problem.getMins();
        double[] maxs = problem.getMaxs();

        for (int i = 0; i < populationSize; i++) {
            population[i] = new Solution(numberOfVariables, numberOfObjectives, mins, maxs);

            problem.evaluate(population[i]);
        }
    }

    /**
     * Populates the given array with {@link #population} parents and {@link #populationSize} generated children.
     *
     * @param union the array to populate
     */
    private void populate(Solution[] union) {
        System.arraycopy(population, 0, union, 0, populationSize);

        int childrenCount = 0;
        while (childrenCount < populationSize) {
            Solution firstParent = selection.from(population);
            Solution secondParent = selection.from(population);
            Solution[] children = crossover.of(firstParent, secondParent);
            mutation.mutate(children);

            for (Solution child : children) {
                union[populationSize + childrenCount] = child;
                problem.evaluate(child);

                childrenCount++;
                if (childrenCount == populationSize) {
                    break;
                }
            }
        }
    }

    /**
     * Returns the fronts obtained by performing a non-dominated sort of the given population.
     *
     * @param population the population to split into fronts
     * @return the fronts obtained by performing a non-dominated sort of the given population
     */
    private List<List<Solution>> buildFronts(Solution[] population) {
        List<List<Solution>> dominates = new ArrayList<>(population.length);
        int[] dominatedBy = new int[population.length];

        List<Solution> initialFront = new ArrayList<>();
        for (int i = 0; i < population.length; i++) {
            dominates.add(new ArrayList<>());

            for (int j = 0; j < population.length; j++) {
                if (i == j) {
                    continue;
                }

                if (dominates(population[i], population[j])) {
                    dominates.get(i).add(population[j]);
                } else if (dominates(population[j], population[i])) {
                    dominatedBy[i]++;
                }
            }

            if (dominatedBy[i] == 0) {
                initialFront.add(population[i]);
                population[i].rank = 0;
            }
        }

        // non-dominated sort
        int frontIndex = 1;
        List<List<Solution>> fronts = new ArrayList<>();
        List<Solution> currentFront = initialFront;
        while (!currentFront.isEmpty()) {
            crowdingSort(currentFront);
            fronts.add(currentFront);
            List<Solution> nextFront = new ArrayList<>();

            for (int i = 0, frontSize = currentFront.size(); i < frontSize; i++) {
                for (int j = 0, dominatesCount = dominates.get(i).size(); j < dominatesCount; j++) {
                    dominatedBy[j]--;

                    if (dominatedBy[j] == 0) {
                        nextFront.add(population[j]);
                        population[j].rank = frontIndex;
                    }
                }
            }

            frontIndex++;
            currentFront = nextFront;
        }

        return fronts;
    }

    /**
     * Returns {@code true} if the first solution dominates the second solution.
     *
     * @param first the first solution
     * @param second the second solution
     * @return {@code true} if the first solution dominates the second solution
     */
    private boolean dominates(Solution first, Solution second) {
        boolean isStrictlyBetter = false;

        for (int i = 0; i < first.objectives.length; i++) {
            if (first.objectives[i] > second.objectives[i]) {
                return false;
            } else if (first.objectives[i] < second.objectives[i]) {
                isStrictlyBetter = true;
            }
        }

        return isStrictlyBetter;
    }

    /**
     * Applies the crowding sort algorithm to a given front.
     *
     * @param front the front containing the solutions to be sorted
     */
    private void crowdingSort(List<Solution> front) {
        double[] fmins = problem.getObjectiveMins();
        double[] fmaxs = problem.getObjectiveMaxs();

        front.get(0).crowdingDistance = Double.POSITIVE_INFINITY;
        front.get(front.size() - 1).crowdingDistance = Double.POSITIVE_INFINITY;
        
        for (int i = 0, numberOfObjectives = problem.getNumberOfObjectives(); i < numberOfObjectives; i++) {
            int index = i;
            front.sort(Comparator.comparingDouble(solution -> solution.objectives[index]));

            for (int j = 1, frontSize = front.size(); j < frontSize - 1; j++) {
                Solution solution = front.get(j);

                solution.crowdingDistance += (front.get(j + 1).objectives[i] - front.get(j - 1).objectives[i])
                        / (fmaxs[i] - fmins[i]);
            }
        }
    }
}
