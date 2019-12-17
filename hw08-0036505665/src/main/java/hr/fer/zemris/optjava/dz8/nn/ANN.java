package hr.fer.zemris.optjava.dz8.nn;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.nn.transfer.TransferFunction;

/**
 * The base class for artificial neural network implementations.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class ANN {

    /**
     * The dimensions of this {@link ANN}.
     *
     * Each array element specifies the number of neurons per neural network layer.
     * The length of the array is equal to the number of layers.
     */
    int[] dimensions;

    /**
     * An array of transfer functions per neural network layer
     * (excluding the input layer as it only forwards to the first hidden layer).
     */
    TransferFunction[] transferFunctions;

    /**
     * The dataset to use for learning.
     */
    ReadOnlyDataset dataset;

    /**
     * Constructs an {@link ANN}.
     *
     * @param dimensions the dimensions of this {@link ANN}
     * @param transferFunctions an array of transfer functions per neural network layer (excluding the input layer)
     * @param dataset the dataset to use for learning
     */
    public ANN(int[] dimensions, TransferFunction[] transferFunctions, ReadOnlyDataset dataset) {
        this.dimensions = dimensions;
        this.transferFunctions = transferFunctions;
        this.dataset = dataset;
    }

    /**
     * Returns the number of inputs in this {@link ANN}.
     *
     * @return the number of inputs in this {@link ANN}
     */
    public int getInputsCount() {
        return dimensions[0];
    }

    /**
     * Returns the number of outputs in this {@link ANN}.
     *
     * @return the number of outputs in this {@link ANN}
     */
    public int getOutputsCount() {
        return dimensions[dimensions.length - 1];
    }

    /**
     * Returns the number of neurons in this {@link ANN}.
     *
     * @return the number of neurons in this {@link ANN}
     */
    public int getNeuronCount() {
        int neuronCount = 0;

        for (int dimension : dimensions) {
            neuronCount += dimension;
        }

        return neuronCount;
    }

    /**
     * Returns the number of weights that this {@link ANN} requires.
     *
     * @return the number of weights that this {@link ANN} requires
     */
    public abstract int getWeightsCount();

    /**
     * Calculates the outputs of this {@link ANN} and stores them in the given {@code outputs} array.
     *
     * @param inputs the inputs of this {@link ANN}
     * @param outputs the outputs array to use for storing the calculated values
     * @param parameters the {@link ANN} parameters to use
     * @throws IllegalArgumentException if the given arrays are of invalid length
     */
    public abstract void calculateOutputs(double[] inputs, double[] outputs, double[] parameters);
}
