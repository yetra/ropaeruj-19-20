package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

/**
 * An implementation of {@link IDecoder} for decoding {@link DoubleArraySolution} objects.
 *
 * @author Bruna Dujmović
 *
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

    @Override
    public double[] decode(DoubleArraySolution solution) {
        return null;
    }

    @Override
    public void decode(DoubleArraySolution solution, double[] values) {
    }
}
