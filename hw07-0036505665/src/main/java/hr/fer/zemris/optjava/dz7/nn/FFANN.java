package hr.fer.zemris.optjava.dz7.nn;

import hr.fer.zemris.optjava.dz7.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;

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

            for (int j = 0; j < dimensions[i]; ++j) {
                int[] weightIndexes = new int[dimensions[i - 1]];
                for (int k = 0; k < weightIndexes.length; k++) {
                    weightIndexes[k] = weightIndex++;
                }

                layers[i][j] = new Neuron(inputIndexes, outputIndex++, weightIndexes, transferFunctions[i - 1]);
            }
        }

    }
}
