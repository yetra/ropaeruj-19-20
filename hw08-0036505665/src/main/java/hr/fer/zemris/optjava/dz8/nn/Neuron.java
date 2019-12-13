package hr.fer.zemris.optjava.dz8.nn;

import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;

/**
 * Models a neuron in a neural network.
 *
 * Each neuron knows which input/output and NN weights array elements belong to it by keeping track of
 * their respective indexes.
 *
 * To map the net input to an output, a {@link TransferFunction} specified in the constructor is used.
 *
 * @author Bruna Dujmović
 *
 */
public class Neuron {

    /**
     * The indexes of inputs belonging to this neuron in a given array of inputs.
     */
    private int[] inputIndexes;

    /**
     * The array element index that this neuron should write its output to.
     */
    private int outputIndex;

    /**
     * The indexes of NN weights belonging to this neuron in a given array of weights.
     */
    private int[] weightIndexes;

    /**
     * The transfer function to use for transforming the net input to an output.
     */
    private TransferFunction transferFunction;

    /**
     * Constructs a {@link Neuron}.
     *
     * @param inputIndexes this neuron's indexes in a given array of inputs
     * @param outputIndex this neuron's index in a given array of outputs
     * @param weightIndexes the indexes of this neuron's NN weights
     * @param transferFunction the transfer function to use for transforming the net input to an output
     */
    public Neuron(int[] inputIndexes, int outputIndex, int[] weightIndexes, TransferFunction transferFunction) {
        this.inputIndexes = inputIndexes;
        this.outputIndex = outputIndex;
        this.weightIndexes = weightIndexes;
        this.transferFunction = transferFunction;
    }

    /**
     * Calculates this neuron's output and writes it to the {@code inputs} using the {@link #outputIndex}.
     *
     * @param inputs the inputs array
     * @param weights the NN weights array
     */
    public void calculateOutput(double[] inputs, double[] weights) {
        inputs[outputIndex] = transferFunction.map(getNet(inputs, weights));
    }

    /**
     * Calculates this neuron's net input.
     *
     * @param inputs the inputs array
     * @param weights the NN weights array
     * @return this neuron's net input
     */
    private double getNet(double[] inputs, double[] weights) {
        double net = weights[weightIndexes[0]]; // bias

        for (int i = 0; i < inputIndexes.length; i++) {
            net += inputs[inputIndexes[i]] * weights[weightIndexes[i + 1]];
        }

        return net;
    }
}
