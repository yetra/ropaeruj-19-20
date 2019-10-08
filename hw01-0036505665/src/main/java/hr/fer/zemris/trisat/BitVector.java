package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

/**
 * This class is an immutable representation of a 3-SAT problem solution.
 *
 * The size of the vector determines the number of variables of the problem.
 *
 * Each bit in the vector represents the boolean value of a given variable.
 * The leftmost bit is for the variable x1, the next one is for x2, etc.
 *
 * @author Bruna Dujmović
 *
 */
public class BitVector {

    /**
     * An array of the variables' boolean values.
     */
    boolean[] bits;

    /**
     * Constructs a {@link BitVector} of random boolean values.
     *
     * @param rand the object for generating random values
     * @param numberOfBits the size of the vector
     */
    public BitVector(Random rand, int numberOfBits) {
        bits = new boolean[numberOfBits];

        for (int i = 0; i < numberOfBits; i++) {
            bits[i] = rand.nextBoolean();
        }
    }

    /**
     * Constructs a {@link BitVector} of the given values.
     *
     * @param bits the boolean values of the vector
     */
    public BitVector(boolean... bits) {
        this.bits = bits;
    }

    /**
     * Constructs a {@link BitVector} of the given size with all bits set to zero.
     *
     * @param n the size of the vector
     */
    public BitVector(int n) {
        bits = new boolean[n];
    }

    /**
     * Returns the boolean value of the index-th variable.
     *
     * @param index the index of the variable whose value should be returned
     * @throws IllegalArgumentException if the given index is not in range [1, size]
     * @return the boolean value of the index-th variable
     */
    public boolean get(int index) {
        if (index < 1 || index > getSize()) {
            throw new IllegalArgumentException("Index must be in range [1, size]");
        }
        return false;
    }

    /**
     * Returns the number of variables represented by this vector.
     *
     * @return the number of variables represented by this vector
     */
    public int getSize() {
        return bits.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(bits).replace("true", "1").replace("false", "0");
    }

    /**
     * Returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public MutableBitVector copy() {
        return new MutableBitVector(bits);
    }
}