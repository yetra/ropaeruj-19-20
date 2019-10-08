package hr.fer.zemris.trisat;

/**
 * This class is a mutable representation of a 3-SAT problem solution.
 *
 * @see BitVector
 * @author Bruna Dujmović
 *
 */
public class MutableBitVector extends BitVector {

    /**
     * Constructs a {@link MutableBitVector} of the given values.
     *
     * @param bits the boolean values of the vector
     */
    public MutableBitVector(boolean... bits) {
        super(bits);
    }

    /**
     * Constructs a {@link MutableBitVector} of the given size with all bits set to zero.
     *
     * @param n the size of the vector
     */
    public MutableBitVector(int n) {
        super(n);
    }

    /**
     * Sets the index-th variable's value to the given value.
     *
     * @param index the index of the variable whose value should be set
     * @param value the new value of the variable
     * @throws IllegalArgumentException if the given index is not in range [1, size]
     */
    public void set(int index, boolean value) {
        if (index < 1 || index > getSize()) {
            throw new IllegalArgumentException("Index must be in range [1, size]");
        }

        bits[index - 1] = value;
    }
}
