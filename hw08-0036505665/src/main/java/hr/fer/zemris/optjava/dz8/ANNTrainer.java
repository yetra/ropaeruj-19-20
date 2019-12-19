package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.dataset.ReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.dataset.SantaFeDataset;
import hr.fer.zemris.optjava.dz8.de.DE;
import hr.fer.zemris.optjava.dz8.de.crossover.BinCrossover;
import hr.fer.zemris.optjava.dz8.de.crossover.Crossover;
import hr.fer.zemris.optjava.dz8.de.mutation.Mutation;
import hr.fer.zemris.optjava.dz8.de.mutation.RandMutation;
import hr.fer.zemris.optjava.dz8.function.ErrorFunction;
import hr.fer.zemris.optjava.dz8.function.Function;
import hr.fer.zemris.optjava.dz8.ann.ANN;
import hr.fer.zemris.optjava.dz8.ann.ElmanANN;
import hr.fer.zemris.optjava.dz8.ann.FFANN;
import hr.fer.zemris.optjava.dz8.ann.transfer.HyperbolicTangentFunction;
import hr.fer.zemris.optjava.dz8.ann.transfer.TransferFunction;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * {@link ANNTrainer} is a program that trains an instance of {@link ANN} on the Santa Fe laser dataset.
 *
 * Usage examples:
 * 08-Laser-generated-data.txt tdnn-8x10x1 30 0.0 2000
 * 08-Laser-generated-data.txt tdnn-4x5x4x1 30 0.0 2000
 * 08-Laser-generated-data.txt elman-1x5x4x1 30 0.0 2000
 * 08-Laser-generated-data.txt elman-1x10x1 30 0.0 2000
 *
 * @author Bruna Dujmović
 *
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
     * Cr - the probability of setting a trial vector component to the corresponding mutant vector component.
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

        ANN ann = getANN(args[1]);
        ReadOnlyDataset dataset = SantaFeDataset.fromFile(Paths.get(args[0]), ann.getInputsCount(), LINES_TO_READ);

        Function errorFunction = new ErrorFunction(ann, dataset);
        int dimensions = errorFunction.getDimensions();

        int populationSize = Integer.parseInt(args[2]);
        double errorThreshold = Double.parseDouble(args[3]);
        int maxIterations = Integer.parseInt(args[4]);

        Mutation mutation = new RandMutation(DIFFERENTIAL_WEIGHT);
        Crossover crossover = new BinCrossover(CROSSOVER_PROBABILITY);

        double[] lowerBounds = new double[dimensions];
        double[] upperBounds = new double[dimensions];
        Arrays.fill(lowerBounds, -1.0);
        Arrays.fill(upperBounds, 1.0);

        double[] parameters = new DE(errorFunction, dimensions, populationSize, maxIterations, errorThreshold,
                mutation, crossover, lowerBounds, upperBounds).run();

        printStatistics(parameters, ann, dataset);
    }

    /**
     * Returns an {@link ANN} instance constructed based on the given string.
     *
     * @param annType the string determining the {@link ANN} instance to construct
     * @return the constructed {@link ANN} instance
     */
    private static ANN getANN(String annType) {
        int[] dimensions = null;
        TransferFunction[] transferFunctions = null;

        if (annType.startsWith("tdnn-") || annType.startsWith("elman-")) {
            dimensions = Arrays.stream(annType.split("-")[1].split("x")).mapToInt(Integer::parseInt).toArray();

            if (dimensions[dimensions.length - 1] != 1) {
                throw new IllegalArgumentException("Invalid ANN dimensions!");
            }

            transferFunctions = new TransferFunction[dimensions.length - 1];
            Arrays.fill(transferFunctions, new HyperbolicTangentFunction());

        } else {
            System.out.println("Unknown neural network type " + annType);
            System.exit(1);
        }

        if (annType.startsWith("tdnn-")) {
            return new FFANN(dimensions, transferFunctions, null);

        } else {
            if (dimensions[0] != 1) {
                throw new IllegalArgumentException("Invalid ANN dimensions!");
            }

            return new ElmanANN(dimensions, transferFunctions, null);
        }
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

            Arrays.stream(inputs).forEach(i -> System.out.format("%7.4f ", i));
            System.out.print("\t");
            Arrays.stream(outputs).forEach(o -> System.out.format("%7.4f ", o));
            System.out.print("=> Prediction: ");
            Arrays.stream(nnOutputs).forEach(nno -> System.out.format("%7.4f ", nno));
            System.out.println();
        }
    }
}
