package hr.fer.zemris.optjava.dz9.problem;

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
     * The lowest possible values of each dimension in the solution space.
     */
    private static final double[] MINS = new double[] {0.1, 0};

    /**
     * The highest possible values of each dimension in the solution space.
     */
    private static final double[] MAXS = new double[] {1, 5};

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
    public void evaluate(double[] solution, double[] objectives) {
        if (solution.length != 2 || objectives.length != 2) {
            throw new IllegalArgumentException("Invalid array sizes, must be 2!");
        }

        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1]) / solution[0];
    }
}
