package hr.fer.zemris.optjava.dz10;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that represents an NSGA-II solution.
 *
 * @author Bruna Dujmović
 *
 */
public class Solution {

    /**
     * The variables of this solution.
     */
    public double[] variables;

    /**
     * The objectives of this solution.
     */
    public double[] objectives;

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
}
