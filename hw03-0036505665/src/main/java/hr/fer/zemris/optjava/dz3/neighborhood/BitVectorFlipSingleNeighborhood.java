package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link INeighborhood} implementation that generates {@link BitVectorSolution} neighbors
 * by flipping a single randomly chosen bit.
 *
 * @author Bruna Dujmović
 */
public class BitVectorFlipSingleNeighborhood implements INeighborhood<BitVectorSolution> {

    @Override
    public BitVectorSolution randomNeighbor(BitVectorSolution solution) {
        int length = solution.getBits().length;
        int randomIndex = ThreadLocalRandom.current().nextInt(length);

        BitVectorSolution neighbor = solution.duplicate();
        boolean[] neighborBits = neighbor.getBits();
        neighborBits[randomIndex] = !neighborBits[randomIndex];

        return neighbor;
    }
}
