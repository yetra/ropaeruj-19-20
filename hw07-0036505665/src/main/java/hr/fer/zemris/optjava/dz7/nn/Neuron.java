package hr.fer.zemris.optjava.dz7.nn;

import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;

/**
 * Models a neuron in a neural network.
 *
 * Each neuron knows which {@code inputs}, {@code outputs} and {@code weights} array elements belong to it
 * by keeping track of their respective indexes.
 *
 * To map the net input to an output, a {@link TransferFunction} specified in the constructor is used.
 *
 * @author Bruna Dujmović
 *
 */
public class Neuron {

    /**
     * The indexes determining which inputs belong to this neuron in a given {@code inputs} array.
     */
    private int[] inputIndexes;

    /**
     * The index of the {@code outputs} array element that this neuron should write to.
     */
    private int outputIndex;

    /**
     * The indexes of {@link FFANN} weights belonging to this neuron in a given {@code weights} array.
     */
    private int[] weightIndexes;

    /**
     * The transfer function to use for transforming the net input to an output.
     */
    private TransferFunction transferFunction;

    /**
     * Constructs a {@link Neuron}.
     *
     * @param inputIndexes this neuron's indexes in a given {@code inputs} array
     * @param outputIndex this neuron's index in a given {@code outputs} array
     * @param weightIndexes the indexes of this neuron's {@link FFANN} weights
     * @param transferFunction the transfer function to use for transforming the net input to an output
     */
    public Neuron(int[] inputIndexes, int outputIndex, int[] weightIndexes, TransferFunction transferFunction) {
        this.inputIndexes = inputIndexes;
        this.outputIndex = outputIndex;
        this.weightIndexes = weightIndexes;
        this.transferFunction = transferFunction;
    }
}
