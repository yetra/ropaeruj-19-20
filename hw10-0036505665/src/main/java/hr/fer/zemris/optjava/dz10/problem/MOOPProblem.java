package hr.fer.zemris.optjava.dz10.problem;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * An interface containing the methods used to describe an MOOP problem.
 *
 * Each {@link MOOPProblem} class should know the dimensions of its objective and solution spaces,
 * and should be able to evaluate a given solution.
 *
 * @author Bruna Dujmović
 *
 */
public interface MOOPProblem {

    /**
     * Returns the dimensions of the objective space.
     *
     * @return the dimensions of the objective space
     */
    int getNumberOfObjectives();

    /**
     * Returns the dimensions of the solution space.
     *
     * @return the dimensions of the solution space
     */
    int getNumberOfVariables();

    /**
     * Returns the lowest possible values of each dimension in the solution space.
     *
     * @return the lowest possible values of each dimension in the solution space
     */
    double[] getMins();

    /**
     * Returns the highest possible values of each dimension in the solution space.
     *
     * @return the highest possible values of each dimension in the solution space
     */
    double[] getMaxs();

    /**
     * Returns the lowest possible values of each objective.
     *
     * @return the lowest possible values of each objective
     */
    double[] getObjectiveMins();

    /**
     * Returns the highest possible values of each objective.
     *
     * @return the highest possible values of each objective
     */
    double[] getObjectiveMaxs();

    /**
     * Evaluates the given solution.
     *
     * @param solution the solution to evaluate
     * @throws IllegalArgumentException if the solution is of invalid size
     */
    void evaluate(Solution solution);
}

