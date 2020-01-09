package hr.fer.zemris.optjava.dz10.crossover;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * An implementation of arithmetic crossover.
 *
 * @author Bruna Dujmović
 *
 */
public class ArithmeticCrossover implements Crossover {

    /**
     * The weighting factor.
     */
    private double alpha;

    /**
     * The lowest possible values of each solution variable.
     */
    private double[] mins;

    /**
     * The highest possible values of each solution variable.
     */
    private double[] maxs;

    /**
     * Constructs an {@link ArithmeticCrossover} instance.
     *
     * @param alpha the weighting factor
     * @param mins the lowest possible values of each solution variable
     * @param maxs the highest possible values of each solution variable
     */
    public ArithmeticCrossover(double alpha, double[] mins, double[] maxs) {
        this.alpha = alpha;
        this.mins = mins;
        this.maxs = maxs;
    }

    @Override
    public Solution[] of(Solution firstParent, Solution secondParent) {
        Solution firstChild = new Solution(firstParent.variables.length, firstParent.objectives.length);
        Solution secondChild = new Solution(firstParent.variables.length, firstParent.objectives.length);

        for (int i = 0; i < firstParent.variables.length; i++) {
            firstChild.variables[i] = alpha * firstParent.variables[i] + (1 - alpha) * secondParent.variables[i];
            checkBounds(i, firstChild.variables);

            secondChild.variables[i] = (1 - alpha) * firstParent.variables[i] + alpha * secondParent.variables[i];
            checkBounds(i, secondChild.variables);
        }

        return new Solution[] {firstChild, secondChild};
    }

    /**
     * Checks if the value on the specified index is between {@code mins[i]} and {@code maxs[i]},
     * and corrects it accordingly.
     *
     * @param i the index of the value to check
     * @param variables the variables whose value should be checked
     */
    private void checkBounds(int i, double[] variables) {
        if (variables[i] < mins[i]) {
            variables[i] = mins[i];
        } else if (variables[i] > maxs[i]) {
            variables[i] = maxs[i];
        }
    }
}
