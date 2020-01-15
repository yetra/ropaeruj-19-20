package hr.fer.zemris.optjava.dz10;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that represents an NSGA-II solution.
 *
 * @author Bruna Dujmović
 *
 */
public class Solution implements Comparable<Solution> {

    /**
     * A comparator to be used in {@link #compareTo(Solution)} for comparing solutions in crowded tournament selection.
     */
    private static final Comparator<Solution> CTS_COMPARATOR = Comparator.comparingDouble((Solution s) -> s.rank)
                                                                         .reversed()
                                                                         .thenComparingDouble(s -> s.crowdingDistance);

    /**
     * The variables of this solution.
     */
    public double[] variables;

    /**
     * The objectives of this solution.
     */
    public double[] objectives;

    /**
     * A list of solution that this solution dominates.
     */
    public List<Solution> dominates;

    /**
     * The number of solutions that this solution is dominated by.
     */
    public double dominatedBy;

    /**
     * The rank of this solution - the index of the front in which this solution was sorted.
     */
    public double rank;

    /**
     * The crowding distance of this solution.
     */
    public double crowdingDistance;

    /**
     * Constructs a {@link Solution} with the {@link #variables} and {@link #objectives} arrays initialized to zero.
     *
     * @param numberOfVariables the length of the {@link #variables} array
     * @param numberOfObjectives the length of the {@link #objectives} array
     */
    public Solution(int numberOfVariables, int numberOfObjectives) {
        variables = new double[numberOfVariables];
        objectives = new double[numberOfObjectives];
    }

    /**
     * Constructs a {@link Solution} of randomized {@link #variables}.
     *
     * @param numberOfVariables the length of the {@link #variables} array
     * @param numberOfObjectives the length of the {@link #objectives} array
     * @param mins the lowest possible values of each variable
     * @param maxs the highest possible values of each variable
     */
    public Solution(int numberOfVariables, int numberOfObjectives, double[] mins, double[] maxs) {
        variables = new double[numberOfVariables];
        objectives = new double[numberOfObjectives];

        randomize(mins, maxs);
    }

    /**
     * Randomizes the {@link #variables} of this solution.
     *
     * @param mins the lowest possible values of each variable
     * @param maxs the highest possible values of each variable
     */
    public void randomize(double[] mins, double[] maxs) {
        for (int i = 0; i < variables.length; i++) {
            variables[i] = ThreadLocalRandom.current().nextDouble(mins[i], maxs[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Arrays.equals(variables, solution.variables);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(variables);
    }

    @Override
    public int compareTo(Solution o) {
        return CTS_COMPARATOR.compare(this, o);
    }
}
