package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.Arrays;

public class IntArrayGASolution extends GASolution<int[]> {

    public IntArrayGASolution(int[] data) {
        this.data = data;
    }

    @Override
    public GASolution<int[]> duplicate() {
        return new IntArrayGASolution(Arrays.copyOf(data, data.length));
    }
}
