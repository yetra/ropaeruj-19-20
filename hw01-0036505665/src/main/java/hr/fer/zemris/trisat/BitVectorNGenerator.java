package hr.fer.zemris.trisat;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class generates all the neighboring {@link BitVector}s of a given vector.
 * A neighborhood contains all the vectors that differ from the given vector in only one bit.
 *
 * @author Bruna Dujmović
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {

    /**
     * The vector whose neighborhood should be generated.
     */
    private BitVector vector;

    /**
     * Constructs a {@link BitVectorNGenerator} for the given vector.
     *
     * @param assignment the vector whose neighborhood should be generated
     */
    public BitVectorNGenerator(BitVector assignment) {
        vector = assignment;
    }

    /**
     * Returns an {@link Iterator} for iterating over the vector's neighborhood.
     *
     * @return an {@link Iterator} for iterating over the vector's neighborhood
     */
    @Override
    public Iterator<MutableBitVector> iterator() {

        return new Iterator<>() {

            // The index of the currently differing bit.
            private int currentIndex = 1;

            @Override
            public boolean hasNext() {
                return currentIndex <= vector.getSize();
            }

            @Override
            public MutableBitVector next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                MutableBitVector neighbor = vector.copy();
                neighbor.set(currentIndex, !vector.get(currentIndex));
                currentIndex++;

                return neighbor;
            }
        };
    }

    /**
     * Returns an array containing the vector's neighbors.
     *
     * @return an array containing the vector's neighbors
     */
    public MutableBitVector[] createNeighborhood() {
        int size = vector.getSize();
        Iterator<MutableBitVector> iterator = iterator();
        MutableBitVector[] neighborhood = new MutableBitVector[size];

        for (int i = 0; i < size; i++) {
            neighborhood[i] = iterator.next();
        }

        return neighborhood;
    }
}
