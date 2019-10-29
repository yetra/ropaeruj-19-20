package hr.fer.zemris.optjava.dz4.part1;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a chromosome to be used in {@link GeneticAlgorithm}
 * that is based on an array of doubles.
 *
 * @author Bruna Dujmović
 * 
 */
public class Chromosome implements Comparable<Chromosome> {

    /**
     * The values of the chromosome.
     */
    public double[] values;

    /**
     * The fitness of the chromosome.
     */
    public double fitness;

    /**
     * Constructs a random {@link Chromosome} of the specified size.
     *
     * @param size the size of the chromosome
     */
    public Chromosome(int size) {
        values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = ThreadLocalRandom.current().nextDouble();
        }
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
