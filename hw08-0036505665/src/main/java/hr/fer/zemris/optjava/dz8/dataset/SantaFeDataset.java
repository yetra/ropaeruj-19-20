package hr.fer.zemris.optjava.dz8.dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Santa Fe laser dataset.
 *
 * @author Bruna Dujmović
 *
 */
public class SantaFeDataset implements ReadOnlyDataset {

    /**
     * The inputs per sample in this dataset.
     */
    private double[][] inputs;

    /**
     * The outputs per sample in this dataset.
     */
    private double[] outputs;

    /**
     * Constructs a {@link SantaFeDataset}.
     *
     * @param inputs the inputs per sample in this dataset
     * @param outputs the outputs per sample in this dataset
     */
    public SantaFeDataset(double[][] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public int getSamplesCount() {
        return inputs.length;
    }

    @Override
    public int getInputsCount() {
        return inputs.length * inputs[0].length;
    }

    @Override
    public int getOutputsCount() {
        return 1;
    }

    @Override
    public double[] getInput(int index) {
        return Arrays.copyOf(inputs[index], getInputsCount());
    }

    @Override
    public double[] getOutput(int index) {
        return new double[] {outputs[index]};
    }
}
