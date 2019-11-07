package hr.fer.zemris.optjava.dz5.ga.chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
     * @param distanceMatrix the QAP distance matrix
     * @param flowMatrix the QAP flow matrix
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
     * @param distanceMatrix the QAP distance matrix
     * @param flowMatrix the QAP flow matrix
     */
    private PermutationChromosome(Integer[] values, int[][] distanceMatrix, int[][] flowMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.flowMatrix = flowMatrix;
        this.values = values;
    }

    @Override
    public void randomize() {
        List<Integer> valuesList = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) {
            valuesList.add(i);
        }

        Collections.shuffle(valuesList);
        values = valuesList.toArray(values);
    }

    @Override
    public Chromosome<Integer> copy() {
        return new PermutationChromosome(Arrays.copyOf(values, values.length), distanceMatrix, flowMatrix);
    }

    @Override
    public void calculateFitness() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                fitness += flowMatrix[i][j] * distanceMatrix[values[i]][values[j]];
            }
        }

        fitness = -fitness;
    }
}
