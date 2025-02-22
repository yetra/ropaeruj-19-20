package hr.fer.zemris.optjava.dz8.function;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.ann.ANN;

import java.util.Arrays;

/**
 * Models the mean squared error (MSE) function - used to determine how wrong
 * a neural network's outputs are compared to the original dataset.
 *
 * @author Bruna Dujmović
 * 
 */
public class ErrorFunction implements Function {

    /**
     * The neural network used for calculating the error.
     */
    private ANN neuralNetwork;

    /**
     * The dataset used for calculating the error.
     */
    private ReadOnlyDataset dataset;

    /**
     * Constructs an {@link ErrorFunction}.
     *
     * @param neuralNetwork the neural network used for calculating the error
     * @param dataset the dataset used for calculating the error
     */
    public ErrorFunction(ANN neuralNetwork, ReadOnlyDataset dataset) {
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
    }

    @Override
    public int getDimensions() {
        return neuralNetwork.getParametersCount();
    }

    @Override
    public double valueAt(double[] vector) {
        double[] vectorCopy = Arrays.copyOf(vector, vector.length);
        double[] nnOutputs = new double[dataset.getOutputsCount()];
        double value = 0;

        for (int s = 0, samplesCount = dataset.getSamplesCount(); s < samplesCount; s++) {
            double[] sampleOutputs = dataset.getOutput(s);
            neuralNetwork.calculateOutputs(dataset.getInput(s), nnOutputs, vectorCopy);

            for (int o = 0, outputsCount = dataset.getOutputsCount(); o < outputsCount; o++) {
                value += Math.pow(sampleOutputs[o] - nnOutputs[o], 2);
            }
        }

        return value / dataset.getSamplesCount();
    }
}
