package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.Arrays;

/**
 * A {@link GASolution} whose {@link #data} is an array of integers.
 *
 * @author Bruna Dujmović
 *
 */
public class IntArrayGASolution extends GASolution<int[]> {

    /**
     * Constructs an {@link IntArrayGASolution} of the given data.
     *
     * @param data the solution data
     */
    public IntArrayGASolution(int[] data) {
        this.data = data;
    }

    @Override
    public GASolution<int[]> duplicate() {
        return new IntArrayGASolution(Arrays.copyOf(data, data.length));
    }

    @Override
    public String toString() {
        return "data=" + Arrays.toString(data) + ", fitness=" + fitness;
    }
}
