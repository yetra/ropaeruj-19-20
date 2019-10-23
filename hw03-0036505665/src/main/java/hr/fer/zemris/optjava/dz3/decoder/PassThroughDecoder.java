package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.Arrays;

/**
 * An implementation of {@link IDecoder} for decoding {@link DoubleArraySolution} objects.
 *
 * @author Bruna Dujmović
 *
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

    @Override
    public double[] decode(DoubleArraySolution solution) {
        double[] values = solution.getValues();

        return Arrays.copyOf(values, values.length);
    }

    @Override
    public void decode(DoubleArraySolution solution, double[] values) {
        values = decode(solution);
    }
}
