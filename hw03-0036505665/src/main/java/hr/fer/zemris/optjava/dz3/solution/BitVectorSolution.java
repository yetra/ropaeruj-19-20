package hr.fer.zemris.optjava.dz3.solution;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Models an optimization algorithm solution using on a bit vector.
 *
 * @author Bruna Dujmović
 */
public class BitVectorSolution extends SingleObjectiveSolution {

    /**
     * An array of bits representing this solution.
     */
    private boolean[] bits;

    /**
     * Constructs a {@link BitVectorSolution} of the specified size with all the bits
     * set to 0.
     *
     * @param size the size of the solution (the length of the bit array)
     */
    public BitVectorSolution(int size) {
        bits = new boolean[size];
    }

    /**
     * Constructs a {@link BitVectorSolution} out of the given boolean array.
     *
     * @param bits the boolean array representing the solution
     */
    public BitVectorSolution(boolean[] bits) {
        this.bits = bits;
    }

    /**
     * Returns the array of bits representing this solution.
     *
     * @return the array of bits representing this solution
     */
    public boolean[] getBits() {
        return bits;
    }

    /**
     * Returns a new {@link BitVectorSolution} that is a duplicate of this solution.
     *
     * @return a new {@link BitVectorSolution} that is a duplicate of this solution
     */
    public BitVectorSolution duplicate() {
        return new BitVectorSolution(Arrays.copyOf(bits, bits.length));
    }

    /**
     * Randomizes the bits of this solution.
     */
    public void randomize() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = ThreadLocalRandom.current().nextBoolean();
        }
    }
}
