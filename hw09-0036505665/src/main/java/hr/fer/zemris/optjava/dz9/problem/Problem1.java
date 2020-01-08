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

    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public int getNumberOfVariables() {
        return 4;
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
