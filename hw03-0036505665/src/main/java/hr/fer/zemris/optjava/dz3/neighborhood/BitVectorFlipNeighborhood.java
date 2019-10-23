package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

/**
 * An {@link INeighborhood} implementation that generates {@link BitVectorSolution} neighbors
 * by flipping random bits.
 *
 * @author Bruna Dujmović
 */
public class BitVectorFlipNeighborhood implements INeighborhood<BitVectorSolution> {

    @Override
    public BitVectorSolution randomNeighbor(BitVectorSolution solution) {
        BitVectorSolution neighbor = solution.duplicate();
        neighbor.randomize();

        return neighbor;
    }
}
