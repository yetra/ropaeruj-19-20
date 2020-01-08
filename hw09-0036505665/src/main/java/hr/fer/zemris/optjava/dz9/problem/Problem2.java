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

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfVariables() {
        return 2;
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
