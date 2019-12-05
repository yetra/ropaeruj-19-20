package hr.fer.zemris.optjava.dz7.function;

import hr.fer.zemris.optjava.dz7.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.nn.FFANN;

/**
 * Models an error function that determines how wrong a neural network's outputs are
 * compared to the original dataset.
 *
 * @author Bruna Dujmović
 * 
 */
public class ErrorFunction implements Function {

    /**
     * The neural network used for calculating the error.
     */
    private FFANN neuralNetwork;

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
    public ErrorFunction(FFANN neuralNetwork, ReadOnlyDataset dataset) {
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
    }

    @Override
    public int getDimensions() {
        return neuralNetwork.getWeightsCount();
    }

    @Override
    public double valueAt(double[] point) {
        double value = 0;

        for (int s = 0, samplesCount = dataset.getSamplesCount(); s < samplesCount; s++) {
            double[] sampleOutputs = dataset.getOutput(s);
            double[] nnOutputs = new double[dataset.getOutputsCount()];
            neuralNetwork.calculateOutputs(dataset.getInput(s), nnOutputs, point);

            for (int o = 0, outputsCount = dataset.getOutputsCount(); o < outputsCount; o++) {
                value += Math.pow(sampleOutputs[o] - nnOutputs[o], 2);
            }
        }

        return value / dataset.getSamplesCount();
    }
}
