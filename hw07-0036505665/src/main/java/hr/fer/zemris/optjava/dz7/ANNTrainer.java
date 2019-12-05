package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.dz7.clonalg.CLONALG;
import hr.fer.zemris.optjava.dz7.function.ErrorFunction;
import hr.fer.zemris.optjava.dz7.function.Function;
import hr.fer.zemris.optjava.dz7.nn.FFANN;
import hr.fer.zemris.optjava.dz7.nn.transfer.SigmoidFunction;
import hr.fer.zemris.optjava.dz7.nn.transfer.TransferFunction;
import hr.fer.zemris.optjava.dz7.pso.GlobalNeighborhood;
import hr.fer.zemris.optjava.dz7.pso.LocalNeighborhood;
import hr.fer.zemris.optjava.dz7.pso.PSO;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * {@link ANNTrainer} is a program that trains an instance of {@link FFANN} on the Iris dataset.
 *
 * Usage examples:
 * 07-iris-formatirano.data pso-a 20 0.01 1000
 * 07-iris-formatirano.data pso-b-1 50 0.01 1000
 * 07-iris-formatirano.data clonalg 30 0.01 1000
 *
 * @author Bruna Dujmović
 */
public class ANNTrainer {

    /**
     * The main method. Loads the Iris dataset and trains the {@link FFANN} using the specified algorithm.
     *
     * @param args the command-line arguments, 5 expected - filePath: a path to the Iris data file,
     *             alg: code of the algorithm to use (pso-a/pso-b-#/clonalg),
     *             populationSize: the size of an algorithm's population,
     *             minError: the error threshold for terminating the algorithms,
     *             maxIterations: the maximum number of algorithm iterations
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Expected 5 arguments, got " + args.length);
            System.exit(1);
        }

        Path filePath = Paths.get(args[0]);
        String algorithmCode = args[1];
        int populationSize = Integer.parseInt(args[2]);
        double minError = Double.parseDouble(args[3]);
        int maxIterations = Integer.parseInt(args[4]);

        ReadOnlyDataset dataset = null;
        try {
            dataset = IrisDataset.fromFile(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        FFANN ffann = new FFANN(new int[] {4,5,3,3}, new TransferFunction[] {new SigmoidFunction(),
                new SigmoidFunction(), new SigmoidFunction()}, dataset);
        Function errorFunction = new ErrorFunction(ffann, dataset);

        int dimensions = errorFunction.getDimensions();

        double[] mins = new double[dimensions];
        double[] maxs = new double[dimensions];
        Arrays.fill(mins, -1.0);
        Arrays.fill(maxs, 1.0);

        // PSO parameters
        double velocityBoundsPercentage = 0.1;
        double c1 = 2.0;
        double c2 = 2.0;

        // CLONALG parameters
        int beta = 2;
        int numberToReplace = 10;
        int c = 1;

        double[] weights = null;
        if (algorithmCode.equals("pso-a")) {
            weights = new PSO(
                    errorFunction, new GlobalNeighborhood(dimensions, true), true, mins, maxs,
                    velocityBoundsPercentage, populationSize, maxIterations, minError, c1, c2
            ).run();

        } else if (algorithmCode.startsWith("pso-b-")) {
            int neighborhoodSize = Integer.parseInt(algorithmCode.split("-")[2]) + 1;

            weights = new PSO(
                    errorFunction, new LocalNeighborhood(neighborhoodSize, populationSize, dimensions, true),
                    true, mins, maxs, velocityBoundsPercentage, populationSize, maxIterations, minError, c1, c2
            ).run();

        } else if (algorithmCode.equals("clonalg")) {
            weights = new CLONALG(
                    errorFunction, maxIterations, minError, populationSize, beta, numberToReplace, mins, maxs, c
            ).run();

        } else {
                System.out.println("Unknown algorithm type " + algorithmCode);
                System.exit(1);
        }

        printStatistics(weights, ffann, dataset);
    }

    /**
     * Prints the statistics for the given {@link FFANN} weights.
     * For each sample in a given dataset, its original inputs and outputs will be printed along with the outputs
     * obtained from the specified neural network.
     *
     * @param weights the {@link FFANN} weights to check
     * @param ffann the neural network
     * @param dataset the dataset to be compared to the neural network's results
     */
    private static void printStatistics(double[] weights, FFANN ffann, ReadOnlyDataset dataset) {
        int correctlyClassified = 0;

        for (int s = 0, samplesCount = dataset.getSamplesCount(); s < samplesCount; s++) {
            double[] inputs = dataset.getInput(s);
            double[] outputs = dataset.getOutput(s);
            double[] nnOutputs = new double[dataset.getOutputsCount()];

            ffann.calculateOutputs(inputs, nnOutputs, weights);
            for (int o = 0; o < nnOutputs.length; o++) {
                nnOutputs[o] = nnOutputs[o] < 0.5 ? 0.0 : 1.0;
            }

            if (Arrays.equals(nnOutputs, outputs)) {
                correctlyClassified++;
            }

            System.out.print(Arrays.toString(inputs) + " : " + Arrays.toString(outputs));
            System.out.println(" => NN: " + Arrays.toString(nnOutputs));
        }

        System.out.println("====================");
        System.out.println("Correctly classified: " + correctlyClassified);
    }
}
