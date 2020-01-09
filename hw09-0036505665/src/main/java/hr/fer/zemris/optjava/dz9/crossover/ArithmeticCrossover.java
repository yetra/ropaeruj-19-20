package hr.fer.zemris.optjava.dz9.crossover;

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
     * The lowest possible values of each dimension in the solution space.
     */
    private double[] mins;

    /**
     * The highest possible values of each dimension in the solution space.
     */
    private double[] maxs;

    /**
     * Constructs an {@link ArithmeticCrossover} instance.
     *
     * @param alpha the weighting factor
     * @param mins the lowest possible values of each dimension in the solution space
     * @param maxs the highest possible values of each dimension in the solution space
     */
    public ArithmeticCrossover(double alpha, double[] mins, double[] maxs) {
        this.alpha = alpha;
        this.mins = mins;
        this.maxs = maxs;
    }

    @Override
    public double[][] of(double[] firstParent, double[] secondParent) {
        double[] firstChild = new double[firstParent.length];
        double[] secondChild = new double[firstParent.length];

        for (int i = 0; i < firstParent.length; i++) {
            firstChild[i] = alpha * firstParent[i] + (1 - alpha) * secondParent[i];
            checkBounds(i, firstChild);

            secondChild[i] = (1 - alpha) * firstParent[i] + alpha * secondParent[i];
            checkBounds(i, secondChild);
        }

        return new double[][] {firstChild, secondChild};
    }

    /**
     * Checks if the value on the specified index is between {@code mins[i]} and {@code maxs[i]},
     * and corrects it accordingly.
     *
     * @param i the index of the value to check
     * @param child the child whose value should be checked
     */
    private void checkBounds(int i, double[] child) {
        if (child[i] < mins[i]) {
            child[i] = mins[i];
        } else if (child[i] > maxs[i]) {
            child[i] = maxs[i];
        }
    }
}
