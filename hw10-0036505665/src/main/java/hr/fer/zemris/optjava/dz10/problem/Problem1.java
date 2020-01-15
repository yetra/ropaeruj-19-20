package hr.fer.zemris.optjava.dz10.problem;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * A {@link MOOPProblem} implementation for minimizing the following functions:
 *
 * f1(x1, x2, x3, x4) = x1^2,
 * f2(x1, x2, x3, x4) = x2^2,
 * f3(x1, x2, x3, x4) = x3^2,
 * f4(x1, x2, x3, x4) = x4^2
 *
 * @author Bruna Dujmović
 *
 */
public class Problem1 implements MOOPProblem {

    /**
     * The lowest possible values of each solution variable.
     */
    private static final double[] MINS = new double[] {-5, -5, -5, -5};

    /**
     * The highest possible values of each solution variable.
     */
    private static final double[] MAXS = new double[] {5, 5, 5, 5};

    /**
     * The lowest possible values of each objective.
     */
    private double[] objectiveMins = new double[] {
            Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE
    };

    /**
     * The highest possible values of each objective.
     */
    private double[] objectiveMaxs = new double[] {
            Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE
    };

    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public int getNumberOfVariables() {
        return 4;
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
        if (solution.variables.length != 4 || solution.objectives.length != 4) {
            throw new IllegalArgumentException("Solution variables and objectives need to be of length 4!");
        }

        for (int i = 0; i < solution.objectives.length; i++) {
            solution.objectives[i] = solution.variables[i] * solution.variables[i];

            if (solution.objectives[i] < objectiveMins[i]) {
                objectiveMins[i] = solution.objectives[i];
            } else if (solution.objectives[i] > objectiveMaxs[i]) {
                objectiveMaxs[i] = solution.objectives[i];
            }
        }
    }
}
