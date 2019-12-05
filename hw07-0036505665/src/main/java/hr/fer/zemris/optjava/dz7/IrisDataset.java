package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;

/**
 * A dataset of Iris flower measurements.
 *
 * @author Bruna Dujmović
 * 
 */
public class IrisDataset implements ReadOnlyDataset {

    /**
     * The number of inputs in each sample of this dataset.
     */
    private static final int INPUTS_COUNT = 4;

    /**
     * The number of outputs in each sample of this dataset.
     */
    private static final int OUTPUTS_COUNT = 3;

    /**
     * The inputs per sample in this dataset.
     *
     * Each input consists of {@link #INPUTS_COUNT} measurements which can be used to identify
     * the species of the Iris.
     */
    private double[][] inputs;

    /**
     * The outputs per sample in this dataset.
     *
     * Each input consists of {@link #OUTPUTS_COUNT} values that represent the different Iris species.
     * Consequently, only one of the values is set to {@code true}.
     */
    private boolean[][] outputs;

    /**
     * Constructs an {@link IrisDataset}.
     *
     * @param inputs the inputs per sample in this dataset
     * @param outputs the outputs per sample in this dataset
     */
    public IrisDataset(double[][] inputs, boolean[][] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public int getSamplesCount() {
        return inputs.length;
    }

    @Override
    public int getInputsCount() {
        return INPUTS_COUNT;
    }

    @Override
    public int getOutputsCount() {
        return OUTPUTS_COUNT;
    }

    @Override
    public double[] getInput(int index) {
        return Arrays.copyOf(inputs[index], getInputsCount());
    }

    @Override
    public boolean[] getOutput(int index) {
        return Arrays.copyOf(outputs[index], getOutputsCount());
    }
}
