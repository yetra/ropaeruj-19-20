package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.selection.Selection;

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
        
        // non-dominated sort inicijalne populacije

        int iteration = 0;
        while (iteration < maxIterations) {

            // generiraj populaciju djece
            // unija roditelja i djece

            // non-dominated sort unije

            // frontu po frontu dodavati u nextPopulation dok stane

            // za frontu koja ne stane
            //     crowding sort
            //     odabrati podskup s najvecim crowding-distance dok se ne popuni nextPopulation

            // sljedece fronte = fronte u populaciji + podskup iz fronte koja ne stane (zadnja fronta)

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
}
