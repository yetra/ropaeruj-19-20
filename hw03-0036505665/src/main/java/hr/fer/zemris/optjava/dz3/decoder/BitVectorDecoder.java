package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

/**
 * An abstract implementation of {@link IDecoder} for decoding {@link BitVectorSolution} objects.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

    /**
     * The minimum values that each solution variable can assume.
     */
    double[] mins;

    /**
     * The maximum values that each solution variable can assume.
     */
    double[] maxs;

    /**
     * An array specifying how many bits are used to encode each solution variable.
     */
    int[] bitsPerVariable;

    /**
     * The total number of variables represented by the solution.
     */
    int n;

    /**
     * Constructs a {@link BitVectorSolution} with the specified parameters.
     *
     * @param mins the minimum values that each solution variable can assume
     * @param maxs the maximum values that each solution variable can assume
     * @param bitsPerVariable an array specifying how many bits are used to encode each solution variable
     * @param n the total number of variables represented by the solution
     */
    BitVectorDecoder(double[] mins, double[] maxs, int[] bitsPerVariable, int n) {
        this.mins = mins;
        this.maxs = maxs;
        this.bitsPerVariable = bitsPerVariable;
        this.n = n;
    }
}
