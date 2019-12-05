package hr.fer.zemris.optjava.dz7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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

    /**
     * Constructs an {@link IrisDataset} from the given file.
     *
     * @param filePath the path to the file containing Iris data
     * @return an {@link IrisDataset} constructed from the given file
     * @throws IOException if an I/O error occurs
     */
    public static IrisDataset fromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);

        int samplesCount = lines.size();
        double[][] inputs = new double[samplesCount][INPUTS_COUNT];
        boolean[][] outputs = new boolean[samplesCount][OUTPUTS_COUNT];

        for (int i = 0; i < samplesCount; i++) {
            String line = lines.get(i);
            String[] parts = line.trim().substring(1, line.length() - 1).split("\\):\\(");

            String[] inputParts = parts[0].split(",");
            for (int j = 0; j < INPUTS_COUNT; j++) {
                inputs[i][j] = Double.parseDouble(inputParts[j]);
            }

            String[] outputParts = parts[1].split(",");
            for (int j = 0; j < OUTPUTS_COUNT; j++) {
                outputs[i][j] = outputParts[i].equals("1");
            }
        }

        return new IrisDataset(inputs, outputs);
    }
}
