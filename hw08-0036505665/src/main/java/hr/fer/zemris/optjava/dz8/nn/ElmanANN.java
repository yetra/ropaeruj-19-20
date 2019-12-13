package hr.fer.zemris.optjava.dz8.nn;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.nn.transfer.TransferFunction;

import java.util.Arrays;

/**
 * Models an Elman artificial neural network.
 *
 * Similar to {@link FFANN}. A context layer exists along with the input layer. Its values are also weighted
 * and forwarded to the first hidden layer without change. The context values are updated using the outputs
 * of the first hidden layer. This enables the network to maintain a sort of state.
 *
 * @author Bruna Dujmović
 *
 */
public class ElmanANN extends ANN {

    /**
     * The layers of this neural network (excluding the input layer).
     */
    private Neuron[][] layers;

    /**
     * Constructs an {@link ElmanANN}.
     *
     * @param dimensions the dimensions of this {@link ElmanANN}
     * @param transferFunctions an array of transfer functions per neural network layer (excluding the input layer)
     * @param dataset the dataset to use for learning
     */
    public ElmanANN(int[] dimensions, TransferFunction[] transferFunctions, ReadOnlyDataset dataset) {
        super(dimensions, transferFunctions, dataset);

        buildLayers();
    }

    /**
     * Returns the size of the context that this {@link ElmanANN} uses.
     *
     * @return the size of the context that this {@link ElmanANN} uses
     */
    public int getContextSize() {
        return dimensions[1];
    }

    @Override
    public int getWeightsCount() {
        if (weightsCount == -1) {
            weightsCount = 0;

            for (int i = 0; i < dimensions.length - 1; i++) {
                weightsCount += dimensions[i] * dimensions[i + 1];
                weightsCount += dimensions[i + 1]; // bias weights
            }

            weightsCount += dimensions[1] * dimensions[1]; // context weights
        }

        return weightsCount;
    }
    
    @Override
    public int getNeuronCount() {
        int neuronCount = dimensions[1]; // context neurons

        for (int dimension : dimensions) {
            neuronCount += dimension;
        }

        return neuronCount;
    }

    /**
     * Calculates the outputs of this {@link ElmanANN} and stores them in the given {@code outputs} array.
     *
     * @param inputs the inputs of this {@link ElmanANN}
     * @param outputs the outputs array to use for storing the calculated values
     * @param parameters the {@link ElmanANN} weights and context to use - this method treats the first
     *                   {@link #weightsCount} array elements as the weights, and the following
     *                   elements as the context
     * @throws IllegalArgumentException if the given arrays are of invalid length
     */
    @Override
    public void calculateOutputs(double[] inputs, double[] outputs, double[] parameters) {
        if (inputs.length != getInputsCount() || outputs.length != getOutputsCount()
                || parameters.length != getWeightsCount() + getContextSize()) {
            throw new IllegalArgumentException("Invalid array length(s) given!");
        }

        double[] values = new double[getNeuronCount()];
        System.arraycopy(inputs, 0, values, 0, inputs.length);
        System.arraycopy(parameters, getWeightsCount(), values, inputs.length, getContextSize()); // initial context

        double[] weights = Arrays.copyOfRange(parameters, 0, getWeightsCount());

        for (int i = 1; i < dimensions.length; i++) {
            Neuron[] layer = layers[i - 1];

            for (Neuron neuron : layer) {
                neuron.calculateOutput(values, weights);
            }

            if (i != 1) {
                continue;
            }
            System.arraycopy(values, inputs.length, parameters, getWeightsCount(), getContextSize()); // update context
        }

        System.arraycopy(values, values.length - outputs.length, outputs, 0, outputs.length);
    }

    /**
     * Builds the layers of this neural network.
     *
     * Since the input layer only forwards inputs without changing them to the first hidden layer,
     * it is not added to the {@link #layers}. The indexes are shifted accordingly.
     *
     * For similar reasons, context neurons are also not added to the {@link #layers}.
     */
    private void buildLayers() {
        int inputIndex = 0;
        int outputIndex = dimensions[0];
        int weightIndex = 0;

        layers = new Neuron[dimensions.length - 1][];

        for (int i = 1; i < dimensions.length; i++) {
            layers[i - 1] = new Neuron[dimensions[i]];

            int inputIndexesLength = dimensions[i - 1];
            if (i == 1) {
                inputIndexesLength += dimensions[i]; // context for the 1st hidden layer
            }

            int[] inputIndexes = new int[inputIndexesLength];
            for (int j = 0; j < inputIndexesLength; j++) {
                inputIndexes[j] = inputIndex++;
            }

            for (int j = 0; j < dimensions[i]; j++) {
                int[] weightIndexes = new int[inputIndexesLength + 1]; // + 1 for the bias
                for (int k = 0; k < weightIndexes.length; k++) {
                    weightIndexes[k] = weightIndex++;
                }

                layers[i - 1][j] = new Neuron(inputIndexes, outputIndex++, weightIndexes, transferFunctions[i - 1]);
            }
        }
    }
}

