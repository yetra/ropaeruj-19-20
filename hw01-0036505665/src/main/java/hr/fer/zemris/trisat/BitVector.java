package hr.fer.zemris.trisat;

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
     * Constructs a {@link BitVector} of random boolean values.
     *
     * @param rand the object for generating random values
     * @param numberOfBits the size of the vector
     */
    public BitVector(Random rand, int numberOfBits) {

    }

    /**
     * Constructs a {@link BitVector} of the given values.
     *
     * @param bits the boolean values of the vector
     */
    public BitVector(boolean... bits) {

    }

    /**
     * Constructs a {@link BitVector} of the given size with all bits set to zero.
     *
     * @param n the size of the vector
     */
    public BitVector(int n) {

    }

    /**
     * Returns the boolean value of the index-th variable.
     *
     * @param index the index of the variable whose value should be returned
     * @return the boolean value of the index-th variable
     */
    public boolean get(int index) {
        return false;
    }

    /**
     * Returns the number of variables represented by this vector.
     *
     * @return the number of variables represented by this vector
     */
    public int getSize() {
        return -1;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * Returns a mutable copy of this vector.
     *
     * @return a mutable copy of this vector
     */
    public MutableBitVector copy() {
        return null;
    }
}
