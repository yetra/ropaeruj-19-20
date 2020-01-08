package hr.fer.zemris.optjava.dz9.problem;

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
     * The lowest possible values of each dimension in the solution space.
     */
    private static final double[] MINS = new double[] {-5, -5, -5, -5};

    /**
     * The highest possible values of each dimension in the solution space.
     */
    private static final double[] MAXS = new double[] {5, 5, 5, 5};

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
    public void evaluate(double[] solution, double[] objectives) {
        if (solution.length != 4 || objectives.length != 4) {
            throw new IllegalArgumentException("Both array sizes must be 4!");
        }

        for (int i = 0; i < objectives.length; i++) {
            objectives[i] = solution[i] * solution[i];
        }
    }
}
