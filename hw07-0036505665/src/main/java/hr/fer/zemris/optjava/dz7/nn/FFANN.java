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
    }
}
