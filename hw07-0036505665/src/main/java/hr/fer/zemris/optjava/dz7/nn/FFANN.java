package hr.fer.zemris.optjava.dz7.nn;

import hr.fer.zemris.optjava.dz7.IrisDataset;
import hr.fer.zemris.optjava.dz7.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.function.ErrorFunction;
import hr.fer.zemris.optjava.dz7.function.Function;
import hr.fer.zemris.optjava.dz7.nn.transfer.SigmoidFunction;
import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;

import java.io.IOException;
import java.nio.file.Paths;
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
     * An array of transfer functions per neural network layer
     * (excluding the input layer as it only forwards to the first hidden layer).
     */
    private TransferFunction[] transferFunctions;

    /**
     * The dataset to use for learning.
     */
    private ReadOnlyDataset dataset;

    /**
     * The layers of this neural network (excluding the input layer).
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
     * @param transferFunctions an array of transfer functions per neural network layer (excluding the input layer)
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
            weightsCount = 0;

            for (int i = 0; i < dimensions.length - 1; i++) {
                weightsCount += dimensions[i] * dimensions[i + 1];
                weightsCount += dimensions[i + 1]; // bias weights
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

    public int getNeuronCount() {
        int neuronCount = 0;

        for (int i = 0; i < dimensions.length; i++) {
            neuronCount += dimensions[i];
        }

        return neuronCount;
    }

    /**
     * Calculates the outputs of this {@link FFANN} and stores them in the given {@code outputs} array.
     *
     * @param inputs the inputs of this {@link FFANN}
     * @param outputs the outputs array to use for storing the calculated values
     * @param weights the {@link FFANN} weights to use
     * @throws IllegalArgumentException if the given arrays are of invalid length
     */
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
     * The main method. Tests the {@link FFANN} implementation using the Iris dataset.
     *
     * new int[] {4, 5, 3, 3}, Arrays.fill(weights, 0.1) -> 0.8365366587431725
     * new int[] {4, 3, 3}, Arrays.fill(weights, 0.1) -> 0.8566740399081082
     * new int[] {4, 3, 3}, Arrays.fill(weights, -0.2) -> 0.7019685477806382
     *
     * @param args the command-line arguments, not used
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        ReadOnlyDataset dataset = IrisDataset.fromFile(Paths.get("07-iris-formatirano.data"));
        FFANN ffann = new FFANN(
                new int[] {4, 5, 3, 3},
                // new int[] {4, 3, 3},
                new TransferFunction[] {new SigmoidFunction(), new SigmoidFunction(), new SigmoidFunction()},
                dataset
        );

        double[] weights = new double[ffann.getWeightsCount()];
        Arrays.fill(weights, 0.1);
        // Arrays.fill(weights, -0.2);

        Function errorFunction = new ErrorFunction(ffann, dataset);
        System.out.println(errorFunction.valueAt(weights));
    }
}
