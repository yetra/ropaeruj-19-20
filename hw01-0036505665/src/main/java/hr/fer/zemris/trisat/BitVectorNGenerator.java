package hr.fer.zemris.trisat;

import java.util.Iterator;

/**
 * This class generates all the neighboring {@link BitVector}s of a given vector.
 * A neighborhood contains all the vectors that differ from the given vector in only one bit.
 *
 * @author Bruna Dujmović
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {

    /**
     * Constructs a {@link BitVectorNGenerator} for the given vector.
     *
     * @param assignment the vector whose neighborhood should be generated
     */
    public BitVectorNGenerator(BitVector assignment) {

    }

    /**
     * Returns an {@link Iterator} for iterating over the vector's neighborhood.
     *
     * @return an {@link Iterator} for iterating over the vector's neighborhood
     */
    @Override
    public Iterator<MutableBitVector> iterator() {
        return null;
    }

    /**
     * Returns an array containing the vector's neighbors.
     *
     * @return an array containing the vector's neighbors
     */
    public MutableBitVector[] createNeighborhood() {
        return null;
    }
}
