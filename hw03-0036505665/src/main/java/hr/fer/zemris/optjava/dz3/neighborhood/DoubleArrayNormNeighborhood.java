package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link INeighborhood} implementation that generated {@link DoubleArraySolution} neighbors
 * using normal distribution.
 *
 * @author Bruna Dujmović
 */
public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {

    /**
     * An array for specifying the range in which each solution variable can be generated.
     */
    private double[] deltas;

    /**
     * Constructs a {@link DoubleArrayNormNeighborhood} with the given deltas.
     *
     * @param deltas an array for specifying the range in which each solution variable can be generated
     */
    public DoubleArrayNormNeighborhood(double[] deltas) {
        this.deltas = deltas;
    }

    @Override
    public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
        return null;
    }
}
