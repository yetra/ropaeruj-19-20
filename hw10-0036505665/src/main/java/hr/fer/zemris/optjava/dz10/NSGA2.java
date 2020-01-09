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
    public List<List<Integer>> run() {
        initialize();

        List<List<Solution>> fronts = nonDominatedSort(population);
        // TODO init crowdingDistances ?

        Solution[] nextPopulation = new Solution[populationSize];
        Solution[] union = new Solution[populationSize * 2];

        int iteration = 0;
        while (iteration < maxIterations) {
            System.arraycopy(population, 0, union, 0, populationSize);

            int childrenCount = 0;
            while (childrenCount < populationSize) {
                Solution[] parents = selection.from(population, 2);
                Solution[] children = crossover.of(parents[0], parents[1]);
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

            fronts = nonDominatedSort(union);

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

            crowdingSort(tooLargeFront, union);

            assert tooLargeFront != null;
            tooLargeFront.sort(Collections.reverseOrder(Comparator.comparingDouble(s -> s.crowdingDistance)));

            for (Solution solution : tooLargeFront) {
                nextPopulation[added++] = solution;

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
     * Returns the fronts obtained by performing a non-dominated sort of the given population.
     *
     * @param population the population to sort
     * @return the fronts obtained by performing a non-dominated sort of the given population
     */
    private List<List<Solution>> nonDominatedSort(Solution[] population) {
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

        int frontIndex = 1;
        List<List<Solution>> fronts = new ArrayList<>();
        List<Solution> currentFront = initialFront;
        while (!currentFront.isEmpty()) {
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
    private void crowdingSort(List<Solution> front, Solution[] population) {
        for (int i = 0; i < population[0].objectives.length; i++) {
            int index = i;
            front.sort(Comparator.comparingDouble(solution -> solution.objectives[index]));

            front.get(0).crowdingDistance = Double.POSITIVE_INFINITY;
            front.get(front.size() - 1).crowdingDistance = Double.POSITIVE_INFINITY;

            for (int j = 1, frontSize = front.size(); j < frontSize - 1; j++) {
                Solution solution = front.get(j);

                solution.crowdingDistance += (population[j + 1].objectives[i] - population[j - 1].objectives[i]);
                // TODO / (fmax - fmin)
            }
        }
    }
}