package hr.fer.zemris.optjava.dz9.problem;

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
     * Evaluates the given solution.
     *
     * @param solution the solution to evaluate
     * @param objectives the array that will contain the evaluation results
     * @throws IllegalArgumentException if any array is of incorrect length
     */
    void evaluate(double[] solution, double[] objectives);
}

