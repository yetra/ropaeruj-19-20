package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link INeighborhood} implementation that generates {@link BitVectorSolution} neighbors
 * by flipping a randomly chosen number of bits.
 *
 * @author Bruna Dujmović
 */
public class BitVectorFlipMultipleNeighborhood implements INeighborhood<BitVectorSolution> {

    @Override
    public BitVectorSolution randomNeighbor(BitVectorSolution solution) {
        int length = solution.getBits().length;
        int numberToFlip = ThreadLocalRandom.current().nextInt(length);

        BitVectorSolution neighbor = solution.duplicate();
        boolean[] neighborBits = neighbor.getBits();

        for (int i = 0; i < numberToFlip; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(length);

            neighborBits[randomIndex] = !neighborBits[randomIndex];
        }

        return neighbor;
    }
}
