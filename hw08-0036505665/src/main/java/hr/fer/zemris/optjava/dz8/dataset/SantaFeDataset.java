package hr.fer.zemris.optjava.dz8.dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
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
        return inputs[0].length;
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

    /**
     * Constructs a {@link SantaFeDataset} from the given file.
     *
     * The dataset will contain {@code linesToRead - timeWindowSize} samples.
     * Each sample will have {@code timeWindowSize} inputs and a single output.
     *
     * @param filePath the path to the file containing Santa Fe data
     * @param timeWindowSize the amount of data (i.e. number of file rows) to treat as the inputs of a single sample
     * @param linesToRead the number of lines to read from the given file (or -1 if the whole file should be read)
     * @return a {@link SantaFeDataset} constructed from the given file
     * @throws IOException if an I/O error occurs
     */
    public static SantaFeDataset fromFile(Path filePath, int timeWindowSize, int linesToRead) throws IOException {
        List<Double> lines = Files.lines(filePath).map(Double::parseDouble).collect(Collectors.toList());
        normalize(lines);

        if (linesToRead != -1) {
            lines = lines.subList(0, linesToRead);
        }

        int samplesCount = lines.size() - timeWindowSize;
        double[][] inputs = new double[samplesCount][timeWindowSize];
        double[] outputs = new double[samplesCount];

        for (int i = 0; i < samplesCount; i++) {
            int j = 0;
            while (j < timeWindowSize) {
                inputs[i][j] = lines.get(i + j);
                j++;
            }

            outputs[i] = lines.get(i + j);
        }

        return new SantaFeDataset(inputs, outputs);
    }

    /**
     * Constructs a {@link SantaFeDataset} from the given file.
     *
     * The dataset will contain {@code linesToRead - 1} samples.
     * Each sample will have a single input and output.
     *
     * @param filePath the path to the file containing Santa Fe data
     * @param linesToRead the number of lines to read from the given file (or -1 if the whole file should be read)
     * @return a {@link SantaFeDataset} constructed from the given file
     * @throws IOException if an I/O error occurs
     */
    public static SantaFeDataset fromFile(Path filePath, int linesToRead) throws IOException {
        return SantaFeDataset.fromFile(filePath, 1, linesToRead);
    }

    /**
     * Normalizes the given stream so that each value is linearly transformed to a value in range [-1, 1].
     *
     * @param values the stream to normalizes
     */
    private static void normalize(List<Double> values) {
        double min = values.get(0);
        double max = values.get(0);

        for (Double value : values) {
            if (value < min) {
                min = value;

            } else if (value > max) {
                max = value;
            }
        }

        for (int i = 0, size = values.size(); i < size; i++) {
            double newValue = ((values.get(i) - min) / (max - min)) * 2 - 1;
            values.set(i, newValue);
        }
    }
}
