package hr.fer.zemris.optjava.dz10.problem;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * A {@link MOOPProblem} implementation for minimizing the following functions:
 *
 * f1(x1, x2) = x1,
 * f2(x1, x2) = (1 + x2) / x1
 *
 * @author Bruna Dujmović
 *
 */
public class Problem2 implements MOOPProblem {

    /**
     * The lowest possible values of each solution variable.
     */
    private static final double[] MINS = new double[] {0.1, 0};

    /**
     * The highest possible values of each solution variable.
     */
    private static final double[] MAXS = new double[] {1, 5};


    /**
     * The lowest possible values of each objective.
     */
    private double[] objectiveMins = new double[2];

    /**
     * The highest possible values of each objective.
     */
    private double[] objectiveMaxs = new double[2];

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfVariables() {
        return 2;
    }

    @Override
    public double[] getMins() {
        return MINS;
    }

    @Override
    public double[] getMaxs() {
        return MAXS;
    }

    @Override
    public double[] getObjectiveMins() {
        return objectiveMins;
    }

    @Override
    public double[] getObjectiveMaxs() {
        return objectiveMaxs;
    }


    @Override
    public void evaluate(Solution solution) {
        if (solution.variables.length != 2 || solution.objectives.length != 2) {
            throw new IllegalArgumentException("Solution variables and objectives need to be of length 2!");
        }

        solution.objectives[0] = solution.variables[0];
        checkObjectiveBounds(solution.objectives[0], 0);

        solution.objectives[1] = (1 + solution.variables[1]) / solution.variables[0];
        checkObjectiveBounds(solution.objectives[1], 1);
    }

    /**
     * Checks and updates {@link #objectiveMins} and {@link #objectiveMaxs} based on the given objective.
     *
     * @param objective the objective to check
     * @param index the index of the objective to check
     */
    private void checkObjectiveBounds(double objective, int index) {
        if (objective < objectiveMins[index]) {
            objectiveMins[index] = objective;
        } else if (objective > objectiveMaxs[index]) {
            objectiveMaxs[index] = objective;
        }
    }
}
