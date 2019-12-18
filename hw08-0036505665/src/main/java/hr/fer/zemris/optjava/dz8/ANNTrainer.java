package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.dataset.SantaFeDataset;
import hr.fer.zemris.optjava.dz8.de.DE;
import hr.fer.zemris.optjava.dz8.function.ErrorFunction;
import hr.fer.zemris.optjava.dz8.function.Function;
import hr.fer.zemris.optjava.dz8.ann.ANN;
import hr.fer.zemris.optjava.dz8.ann.ElmanANN;
import hr.fer.zemris.optjava.dz8.ann.FFANN;
import hr.fer.zemris.optjava.dz8.ann.transfer.HyperbolicTangentFunction;
import hr.fer.zemris.optjava.dz8.ann.transfer.TransferFunction;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * {@link ANNTrainer} is a program that trains an instance of {@link ANN} on the Santa Fe laser dataset.
 *
 * Usage examples:
 * 08-Laser-generated-data.txt tdnn-8x10x1 30 0.02 1000
 * 08-Laser-generated-data.txt elman-1x5x4x1 30 0.02 1000
 *
 * @author Bruna Dujmović
 */
public class ANNTrainer {

    /**
     * The number of lines to read from the Santa Fe laser dataset.
     */
    private static final int LINES_TO_READ = 600;

    /**
     * F - the parameter determining the impact of the vector difference when constructing a mutant vector.
     */
    private static final double DIFFERENTIAL_WEIGHT = 0.7;

    /**
     * Cr - the probability of replacing a trial vector component with a mutant vector component.
     */
    private static final double CROSSOVER_PROBABILITY = 0.02;

    /**
     * The main method. Loads the Iris dataset and trains the {@link FFANN} using the specified algorithm.
     *
     * @param args the command-line arguments, 5 expected - filePath: a path to the Iris data file,
     *             neuralNetworkType: code of the algorithm to use (pso-a/pso-b-#/clonalg),
     *             populationSize: the size of an algorithm's population,
     *             errorThreshold: the error threshold for terminating the algorithms,
     *             maxIterations: the maximum number of algorithm iterations
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.out.println("Expected 5 arguments, got " + args.length);
            System.exit(1);
        }

        Path filePath = Paths.get(args[0]);
        String neuralNetworkType = args[1];
        int populationSize = Integer.parseInt(args[2]);
        double errorThreshold = Double.parseDouble(args[3]);
        int maxIterations = Integer.parseInt(args[4]);

        ANN ann = null;
        ReadOnlyDataset dataset = null;
        if (neuralNetworkType.startsWith("tdnn-")) {
            int[] nnDimensions = Arrays.stream(neuralNetworkType.split("-")[1].split("x"))
                                       .mapToInt(Integer::parseInt).toArray();

            if (nnDimensions[nnDimensions.length - 1] != 1) {
                throw new IllegalArgumentException("Illegal FFANN dimensions!");
            }

            TransferFunction[] transferFunctions = new TransferFunction[nnDimensions.length - 1];
            Arrays.fill(transferFunctions, new HyperbolicTangentFunction());

            dataset = SantaFeDataset.fromFile(filePath, nnDimensions[0], LINES_TO_READ);
            ann = new FFANN(nnDimensions, transferFunctions, dataset);

        } else if (neuralNetworkType.startsWith("elman-")) {
            int[] nnDimensions = Arrays.stream(neuralNetworkType.split("-")[1].split("x"))
                                       .mapToInt(Integer::parseInt).toArray();

            if (nnDimensions[0] != 1 || nnDimensions[nnDimensions.length - 1] != 1) {
                throw new IllegalArgumentException("Illegal ElmanANN dimensions!");
            }

            TransferFunction[] transferFunctions = new TransferFunction[nnDimensions.length - 1];
            Arrays.fill(transferFunctions, new HyperbolicTangentFunction());

            dataset = SantaFeDataset.fromFile(filePath, nnDimensions[0], LINES_TO_READ);
            ann = new ElmanANN(nnDimensions, transferFunctions, dataset);

        } else {
            System.out.println("Unknown neural network type " + neuralNetworkType);
            System.exit(1);
        }

        Function errorFunction = new ErrorFunction(ann, dataset);
        int dimensions = errorFunction.getDimensions();

        double[] lowerBounds = new double[dimensions];
        double[] upperBounds = new double[dimensions];
        Arrays.fill(lowerBounds, -1.0);
        Arrays.fill(upperBounds, 1.0);

        double[] parameters = new DE(errorFunction, dimensions, populationSize, maxIterations, errorThreshold,
                DIFFERENTIAL_WEIGHT, CROSSOVER_PROBABILITY, lowerBounds, upperBounds).run();

        printStatistics(parameters, ann, dataset);
    }

    /**
     * Prints the statistics for the given {@link ANN} parameters.
     * For each sample in a given dataset, its original inputs and outputs will be printed along with the outputs
     * obtained from the specified neural network.
     *
     * @param parameters the {@link ANN} parameters to check
     * @param ann the neural network
     * @param dataset the dataset to be compared to the neural network's results
     */
    private static void printStatistics(double[] parameters, ANN ann, ReadOnlyDataset dataset) {
        for (int s = 0, samplesCount = dataset.getSamplesCount(); s < samplesCount; s++) {
            double[] inputs = dataset.getInput(s);
            double[] outputs = dataset.getOutput(s);
            double[] nnOutputs = new double[dataset.getOutputsCount()];

            ann.calculateOutputs(inputs, nnOutputs, parameters);

            System.out.print("(" + Arrays.toString(inputs) + "), (" + Arrays.toString(outputs) + ")");
            System.out.println(" => Prediction: " + Arrays.toString(nnOutputs));
        }
    }
}
