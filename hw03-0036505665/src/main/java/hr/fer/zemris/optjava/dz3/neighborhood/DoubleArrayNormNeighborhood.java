package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link INeighborhood} implementation that generates {@link DoubleArraySolution} neighbors
 * using normal distribution.
 *
 * @author Bruna Dujmović
 */
public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {

    /**
     * An array of factors for multiplying randomly generated variable values.
     */
    private double[] deltas;

    /**
     * Constructs a {@link DoubleArrayNormNeighborhood} with the given deltas.
     *
     * @param deltas an array of factors for multiplying randomly generated variable values
     */
    public DoubleArrayNormNeighborhood(double[] deltas) {
        this.deltas = deltas;
    }

    @Override
    public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
        DoubleArraySolution neighbor = solution.duplicate();

        double[] values = neighbor.getValues();
        for (int i = 0; i < values.length; i++) {
            values[i] += ThreadLocalRandom.current().nextGaussian() * deltas[i];
        }

        return neighbor;
    }
}
