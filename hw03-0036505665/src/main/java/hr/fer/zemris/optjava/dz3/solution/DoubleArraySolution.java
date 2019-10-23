package hr.fer.zemris.optjava.dz3.solution;

/**
 * Models an optimization algorithm solution that is based on an array of doubles.
 *
 * @author Bruna Dujmović
 *
 */
public class DoubleArraySolution extends SingleObjectiveSolution {

    /**
     * An array containing the values of this solution.
     */
    private double[] values;

    /**
     * Constructs an {@link DoubleArraySolution} of the specified size.
     *
     * @param size the size of the solution (the length of the double array)
     */
    public DoubleArraySolution(int size) {
    }

    /**
     * Constructs a {@link DoubleArraySolution} out of the given double array.
     *
     * @param values the array containing the values of this solution
     */
    public DoubleArraySolution(double[] values) {
    }

    /**
     * Returns the array of doubles representing this solution.
     *
     * @return the array of doubles representing this solution
     */
    public double[] getValues() {
        return null;
    }

    /**
     * Returns a new {@link DoubleArraySolution} that is a duplicate of this solution.
     *
     * @return a new {@link DoubleArraySolution} that is a duplicate of this solution
     */
    public DoubleArraySolution duplicate() {
        return null;
    }

    /**
     * Randomizes the values of this solution.
     *
     * @param mins an array of lower bounds for each of the values in this solution
     * @param maxs an array of upper bounds for each of the values in this solution
     */
    public void randomize(double[] mins, double[] maxs) {
    }
}
