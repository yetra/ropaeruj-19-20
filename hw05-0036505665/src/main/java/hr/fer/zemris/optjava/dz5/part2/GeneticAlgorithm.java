package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.ga.chromosome.PermutationChromosome;
import hr.fer.zemris.optjava.dz5.ga.crossover.OrderBasedCrossover;
import hr.fer.zemris.optjava.dz5.ga.mutation.ExchangeMutation;
import hr.fer.zemris.optjava.dz5.ga.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz5.ga.factor.ConstantCompFactor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * An implementation of SASEGASA for solving the Quadratic Assignment Problem.
 *
 * @author Bruna Dujmović
 *
 */
public class GeneticAlgorithm {

    /**
     * The QAP matrix of distances.
     */
    private static int[][] distanceMatrix;

    /**
     * The QAP flow matrix.
     */
    private static int[][] flowMatrix;

    /**
     * An instance of {@link OffspringSelection} used in SASEGASA.
     */
    private OffspringSelection<Integer> os;

    /**
     * The size of the chromosomes.
     */
    private int chromosomeSize;

    /**
     * The total size of the population.
     */
    private int totalPopSize;

    /**
     * The current number of populations.
     */
    private int popCount;

    /**
     * Constructs an instance of {@link GeneticAlgorithm}.
     *
     * @param os tn instance of {@link OffspringSelection} to use in SASEGASA
     * @param chromosomeSize the size of the chromosomes
     * @param totalPopSize the total size of the population
     * @param popCount the initial number of populations
     */
    public GeneticAlgorithm(OffspringSelection<Integer> os, int chromosomeSize,
                            int totalPopSize, int popCount) {
        this.os = os;
        this.chromosomeSize = chromosomeSize;
        this.totalPopSize = totalPopSize;
        this.popCount = popCount;
    }

    /**
     * Executes the algorithm.
     */
    private void run() {
        List<Chromosome<Integer>> population = getInitialPopulation();

        while (popCount > 0) {
            int popSize = totalPopSize / popCount;
            List<Chromosome<Integer>> newPopulation = new ArrayList<>();

            int start = 0;
            for (int i = 0; i < popCount; i++) {
                List<Chromosome<Integer>> subPopList = population.subList(start, start + popSize);
                start += popSize;

                newPopulation.addAll(os.run(new HashSet<>(subPopList)));
            }

            population = newPopulation;
            popCount--;
        }
    }

    /**
     * Returns a population initialized with random chromosomes.
     */
    private List<Chromosome<Integer>> getInitialPopulation() {
        Set<Chromosome<Integer>> populationSet = new HashSet<>(totalPopSize);

        while (populationSet.size() < totalPopSize) {
            Chromosome<Integer> chromosome = new PermutationChromosome(chromosomeSize, distanceMatrix, flowMatrix);
            chromosome.calculateFitness();
            
            populationSet.add(chromosome);
        }

        return new ArrayList<>(populationSet);
    }

    /**
     * The main method. Reads the QAP data file and executes the algorithm.
     *
     * @param args the command-line arguments, 3 expected - path to data,
     *             total population size, initial number of populations
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Expected 3 arguments, got " + args.length);
        }

        Path dataPath = Paths.get(args[0]);
        int totalPopSize = Integer.parseInt(args[1]);
        int initialPopCount = Integer.parseInt(args[2]);

        int size = 0;
        try (BufferedReader br = Files.newBufferedReader(dataPath)) {
            size = Integer.parseInt(br.readLine());
            br.readLine();
            distanceMatrix = parseMatrix(br, size);
            br.readLine();
            flowMatrix = parseMatrix(br, size);

        } catch (IOException e) {
            System.out.println("I/O error occured!");
            System.exit(1);
        }

        OffspringSelection<Integer> os = new OffspringSelection<>(
                new OrderBasedCrossover<>(), new ExchangeMutation<>(),
                new TournamentSelection<>(2), new ConstantCompFactor(0.7));

        new GeneticAlgorithm(os, size, totalPopSize, initialPopCount).run();
    }

    /**
     * Parses a square matrix of the specified size using the given {@link BufferedReader}.
     *
     * @param br the {@link BufferedReader} to use
     * @param size the size of the matrix
     * @return the parsed matrix
     * @throws IOException if an I/O error occurs
     */
    private static int[][] parseMatrix(BufferedReader br, int size) throws IOException {
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            String[] rowElements = br.readLine().trim().split("\\s+");

            for (int j = 0; j < size; j++) {
                matrix[i][j] = Integer.parseInt(rowElements[j]);
            }
        }

        return matrix;
    }
}
