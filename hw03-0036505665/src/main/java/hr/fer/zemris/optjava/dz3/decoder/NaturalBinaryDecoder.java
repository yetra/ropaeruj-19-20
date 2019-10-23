package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

/**
 * A {@link BitVectorDecoder} that decodes bit vector solutions using the natural binary code.
 *
 * @author Bruna Dujmović
 *
 */
public class NaturalBinaryDecoder extends BitVectorDecoder {

    /**
     * Constructs a {@link NaturalBinaryDecoder} with the specified parameters.
     *
     * @param mins the minimum values that each solution variable can assume
     * @param maxs the maximum values that each solution variable can assume
     * @param bitsPerVariable an array specifying how many bits are used to encode each solution variable
     * @param n the total number of variables represented by the solution
     */
    public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bitsPerVariable, int n) {
        super(mins, maxs, bitsPerVariable, n);
    }

    @Override
    public double[] decode(BitVectorSolution solution) {
        return null;
    }

    @Override
    public void decode(BitVectorSolution solution, double[] values) {
    }
}
