package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.*;

/**
 * An implementation of SASEGASA for solving the Quadratic Assignment Problem.
 *
 * @author Bruna Dujmović
 *
 */
public class GeneticAlgorithm {

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
            Chromosome<Integer> chromosome = new PermutationChromosome(chromosomeSize);
            chromosome.calculateFitness();
            
            populationSet.add(chromosome);
        }

        return new ArrayList<>(populationSet);
    }
}
