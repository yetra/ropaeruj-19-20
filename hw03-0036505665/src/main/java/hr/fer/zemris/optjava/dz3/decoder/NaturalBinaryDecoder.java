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
    /**
     * Constructs a {@link BitVectorDecoder} with the specified parameters.
     * All variables are considered to be encoded with the same number of bits.
     *
     * @param mins the minimum values that each solution variable can assume
     * @param maxs the maximum values that each solution variable can assume
     * @param bitsPerAllVariables how many bits are used to encode a solution variable
     * @param n the total number of variables represented by the solution
     */
    public NaturalBinaryDecoder(double[] mins, double[] maxs, int bitsPerAllVariables, int n) {
        super(mins, maxs, bitsPerAllVariables, n);
    }

    @Override
    public double[] decode(BitVectorSolution solution) {
        double[] values = new double[n];
        decode(solution, values);

        return values;
    }

    @Override
    public void decode(BitVectorSolution solution, double[] values) {
        boolean[] bits = solution.getBits();
        int shift = 0;

        for (int i = 0; i < n; i++) {
            int k = 0;

            for (int j = 0; j < bitsPerVariable[i]; j++) {
                k = (k << 1) + (bits[j + shift] ? 1 : 0);
            }
            shift += bitsPerVariable[i];

            values[i] = mins[i] + k / (Math.pow(2, bitsPerVariable[i]) - 1) * (maxs[i] - mins[i]);
        }
    }
}
