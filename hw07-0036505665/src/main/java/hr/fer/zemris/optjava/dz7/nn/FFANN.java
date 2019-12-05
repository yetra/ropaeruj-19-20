package hr.fer.zemris.optjava.dz7.nn;

import hr.fer.zemris.optjava.dz7.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;

import java.util.Arrays;

/**
 * Models a feed-forward artificial neural network.
 *
 * @author Bruna Dujmović
 *
 */
public class FFANN {

    /**
     * The dimensions of this {@link FFANN}.
     *
     * Each array element specifies the number of neurons per neural network layer.
     * The length of the array is equal to the number of layers.
     */
    private int[] dimensions;

    /**
     * An array of transfer functions per neural network layer.
     */
    private TransferFunction[] transferFunctions;

    /**
     * The dataset to use for learning.
     */
    private ReadOnlyDataset dataset;

    /**
     * The layers of this neural network.
     */
    private Neuron[][] layers;

    /**
     * The number of weights that this {@link FFANN} requires.
     */
    private int weightsCount = -1;

    /**
     * Constructs a {@link FFANN}.
     *
     * @param dimensions the dimensions of this {@link FFANN}
     * @param transferFunctions an array of transfer functions per neural network layer
     * @param dataset the dataset to use for learning
     */
    public FFANN(int[] dimensions, TransferFunction[] transferFunctions, ReadOnlyDataset dataset) {
        this.dimensions = dimensions;
        this.transferFunctions = transferFunctions;
        this.dataset = dataset;

        buildLayers();
    }

    /**
     * Returns the number of weights that this {@link FFANN} requires.
     *
     * @return the number of weights that this {@link FFANN} requires
     */
    public int getWeightsCount() {
        if (weightsCount == -1) {
            weightsCount = dimensions[0];

            for (int i = 0; i < dimensions.length - 1; i++) {
                weightsCount += dimensions[i] * dimensions[i + 1];
            }
        }

        return weightsCount;
    }

    /**
     * Returns the number of inputs of this {@link FFANN}.
     *
     * @return the number of inputs of this {@link FFANN}
     */
    public int getInputsCount() {
        return dimensions[0];
    }

    /**
     * Returns the number of outputs of this {@link FFANN}.
     *
     * @return the number of outputs of this {@link FFANN}
     */
    public int getOutputsCount() {
        return dimensions[dimensions.length - 1];
    }

    /**
     * Calculates the outputs of this {@link FFANN} and stores them in the given {@code outputs} array.
     *
     * @param inputs the inputs of this {@link FFANN}
     * @param outputs the outputs array to use for storing the calculated values
     * @param weights the {@link FFANN} weights to use
     */
    public void calculateOutputs(double[] inputs, double[] outputs, double[] weights) {
        double[] inputsCopy = Arrays.copyOf(inputs, inputs.length);

        for (Neuron[] layer : layers) {
            for (Neuron neuron : layer) {
                neuron.calculateOutput(inputsCopy, weights);
            }
        }

        System.arraycopy(inputsCopy, inputs.length - outputs.length, outputs, 0, outputs.length);
    }

    /**
     * Builds the layers of this neural network.
     */
    private void buildLayers() {
        int inputIndex = 0;
        int outputIndex = dimensions[0];
        int weightIndex = 0;

        layers = new Neuron[dimensions.length - 1][];

        for (int i = 1; i < dimensions.length; i++) {
            layers[i] = new Neuron[dimensions[i]];

            int[] inputIndexes = new int[dimensions[i - 1]];
            for (int j = 0; j < dimensions[i - 1]; j++) {
                inputIndexes[j] = inputIndex++;
            }

            for (int j = 0; j < dimensions[i]; j++) {
                int[] weightIndexes = new int[dimensions[i - 1] + 1]; // + 1 for the bias
                for (int k = 0; k < weightIndexes.length; k++) {
                    weightIndexes[k] = weightIndex++;
                }

                layers[i][j] = new Neuron(inputIndexes, outputIndex++, weightIndexes, transferFunctions[i - 1]);
            }
        }
    }
}
