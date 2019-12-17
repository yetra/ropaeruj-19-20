package hr.fer.zemris.optjava.dz8.nn;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.nn.transfer.HyperbolicTangentFunction;
import hr.fer.zemris.optjava.dz8.nn.transfer.TransferFunction;

import java.util.Arrays;

/**
 * Models a feed-forward artificial neural network.
 *
 * @author Bruna Dujmović
 *
 */
public class FFANN extends ANN {

    /**
     * The layers of this neural network (excluding the input layer).
     */
    private Neuron[][] layers;

    /**
     * Constructs a {@link FFANN}.
     *
     * @param dimensions the dimensions of this {@link FFANN}
     * @param transferFunctions an array of transfer functions per neural network layer (excluding the input layer)
     * @param dataset the dataset to use for learning
     */
    public FFANN(int[] dimensions, TransferFunction[] transferFunctions, ReadOnlyDataset dataset) {
        super(dimensions, transferFunctions, dataset);

        buildLayers();
    }

    @Override
    public int getWeightsCount() {
        if (weightsCount == -1) {
            weightsCount = 0;

            for (int i = 0; i < dimensions.length - 1; i++) {
                weightsCount += dimensions[i] * dimensions[i + 1];
                weightsCount += dimensions[i + 1]; // bias weights
            }
        }

        return weightsCount;
    }

    /**
     * Calculates the outputs of this {@link FFANN} and stores them in the given {@code outputs} array.
     *
     * @param inputs the inputs of this {@link FFANN}
     * @param outputs the outputs array to use for storing the calculated values
     * @param weights the {@link FFANN} weights to use
     * @throws IllegalArgumentException if the given arrays are of invalid length
     */
    @Override
    public void calculateOutputs(double[] inputs, double[] outputs, double[] weights) {
        if (inputs.length != getInputsCount() || outputs.length != getOutputsCount()
                || weights.length != getWeightsCount()) {
            throw new IllegalArgumentException("Invalid array length(s) given!");
        }

        double[] values = new double[getNeuronCount()];
        System.arraycopy(inputs, 0, values, 0, inputs.length);

        for (Neuron[] layer : layers) {
            for (Neuron neuron : layer) {
                neuron.calculateOutput(values, weights);
            }
        }

        System.arraycopy(values, values.length - outputs.length, outputs, 0, outputs.length);
    }

    /**
     * Builds the layers of this neural network.
     *
     * Since the input layer only forwards inputs without changing them to the first hidden layer,
     * it is not added to the {@link #layers}. The indexes are shifted accordingly.
     */
    private void buildLayers() {
        int inputIndex = 0;
        int outputIndex = dimensions[0];
        int weightIndex = 0;

        layers = new Neuron[dimensions.length - 1][];

        for (int i = 1; i < dimensions.length; i++) {
            layers[i - 1] = new Neuron[dimensions[i]];

            int[] inputIndexes = new int[dimensions[i - 1]];
            for (int j = 0; j < dimensions[i - 1]; j++) {
                inputIndexes[j] = inputIndex++;
            }

            for (int j = 0; j < dimensions[i]; j++) {
                int[] weightIndexes = new int[dimensions[i - 1] + 1]; // + 1 for the bias
                for (int k = 0; k < weightIndexes.length; k++) {
                    weightIndexes[k] = weightIndex++;
                }

                layers[i - 1][j] = new Neuron(inputIndexes, outputIndex++, weightIndexes, transferFunctions[i - 1]);
            }
        }
    }

    /**
     * A test program that prints the neuron count and weights count for different {@link FFANN} architectures.
     *
     * @param args the command-line arguments, not used
     */
    public static void main(String[] args) {
        int[][] dimensionsArray = new int[][] {
                new int[] {8, 10, 1},
                new int[] {8, 5, 4, 1},
                new int[] {4, 12, 1},
                new int[] {4, 5, 4, 1}
        };

        for (int[] dimensions : dimensionsArray) {
            TransferFunction[] transferFunctions = new TransferFunction[dimensions.length];
            Arrays.fill(transferFunctions, new HyperbolicTangentFunction());

            ANN ann = new FFANN(dimensions, transferFunctions, null);

            System.out.format("Neurons: %3d, Weights: %3d%n", ann.getNeuronCount(), ann.getWeightsCount());
        }
    }
}
