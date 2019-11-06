package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a chromosome based on an array of integers that will be used in solving
 * the Quadratic Assignment Problem.
 *
 * The allowed integer values are in range [0, {@link #values}.length].
 * A single value can occur only once in the {@link #values} array.
 *
 * @author Bruna Dujmović
 *
 */
public class PermutationChromosome extends Chromosome<Integer> {

    /**
     * The QAP matrix of distances.
     */
    private int[][] distanceMatrix;

    /**
     * The QAP flow matrix.
     */
    private int[][] flowMatrix;

    /**
     * Constructs a random {@link PermutationChromosome} of the specified size.
     *
     * @param size the size of the chromosome
     */
    public PermutationChromosome(int size, int[][] distanceMatrix, int[][] flowMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.flowMatrix = flowMatrix;

        values = new Integer[size];
        randomize();
    }

    /**
     * Constructs a {@link PermutationChromosome} of the given values.
     *
     * @param values the values of the chromosome
     */
    private PermutationChromosome(Integer[] values) {
        this.values = values;
    }

    @Override
    public void randomize() {
        for (int i = 0; i < values.length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(values.length);

            values[randomIndex] = i;
        }
    }

    @Override
    public Chromosome<Integer> copy() {
        return new PermutationChromosome(Arrays.copyOf(values, values.length));
    }

    @Override
    public void calculateFitness() {

    }
}
