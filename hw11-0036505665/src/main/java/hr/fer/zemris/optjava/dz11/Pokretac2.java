package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.ICrossover;
import hr.fer.zemris.generic.ga.IMutation;
import hr.fer.zemris.generic.ga.ISelection;
import hr.fer.zemris.optjava.dz11.ga.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main program for running {@link ParallelChildGenerationGA}.
 *
 * @author Bruna Dujmović
 *
 */
public class Pokretac2 {

    /**
     * The main method. Runs the {@link ParallelEvaluationGA}.
     *
     * @param args the command-line arguments, 7 expected: a path to the original image file,
     *             the number of rectangles to use for approximation, the size of the population,
     *             the number of algorithm iterations, the minimum fitness value to reach before algorithm termination,
     *             a path to the txt file for writing the best solution,
     *             a path to the image file that the algorithm will generate
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if a thread is interrupted
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 7) {
            System.out.println("Expected 7 arguments, got " + args.length);
            System.exit(1);
        }

        GrayScaleImage image = GrayScaleImage.load(new File(args[0]));
        int rectangleCount = Integer.parseInt(args[1]);
        int populationSize = Integer.parseInt(args[2]);
        int maxIterations = Integer.parseInt(args[3]);
        double minFitness = Double.parseDouble(args[4]);
        Path solutionPath = Paths.get(args[5]);
        Path approximatedImagePath = Paths.get(args[6]);

        ISelection<int[]> selection = new TournamentSelection<>(2);
        ICrossover<int[]> crossover = new OnePointCrossover();
        IMutation<int[]> mutation = new ExchangeMutation();
        Evaluator evaluator = new Evaluator(image);

        GASolution<int[]> best = new ParallelChildGenerationGA(
                populationSize, minFitness, maxIterations, rectangleCount,
                image, selection, crossover, mutation, evaluator
        ).run();

        GrayScaleImage approximatedImage = evaluator.draw(best, image);
        approximatedImage.save(approximatedImagePath.toFile());

        writeSolution(best, solutionPath);
    }

    /**
     * Writes the given solution to the specified path.
     *
     * @param solution the solution to write
     * @param solutionPath the destination file path
     * @throws IOException if an I/O error occurs
     */
    private static void writeSolution(GASolution<int[]> solution, Path solutionPath) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(solutionPath)) {
            for (int value : solution.data) {
                bw.write(value + "\n");
            }
        }
    }
}
