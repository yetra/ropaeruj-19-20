package hr.fer.zemris.optjava.dz4.ga;

import hr.fer.zemris.optjava.dz4.part1.GeneticAlgorithm;

import java.util.Arrays;
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
     * The default chromosome size;
     */
    private static final int DEFAULT_SIZE = 6;

    /**
     * The values of the chromosome.
     */
    public double[] values;

    /**
     * The fitness of the chromosome.
     */
    public double fitness;

    /**
     * Constructs a {@link Chromosome} of {@link #DEFAULT_SIZE} with all values set to zero.
     */
    public Chromosome() {
        values = new double[DEFAULT_SIZE];
    }

    /**
     * Constructs a {@link Chromosome} of the specified size with all values set to zero.
     *
     * @param size the size of the chromosome
     */
    public Chromosome(int size) {
        values = new double[size];
    }

    /**
     * Constructs a random {@link Chromosome} of the specified size if {@code randomize}
     * is set to {@code true}. Otherwise, all chromosome values will be set to zero.
     *
     * @param size the size of the chromosome
     * @param randomize {@code true} if {@link #values} should be set to random values
     */
    public Chromosome(int size, boolean randomize) {
        this(size);

        if (randomize) {
            randomize();
        }
    }

    /**
     * Constructs a {@link Chromosome} of the given values.
     *
     * @param values the values of the chromosome
     */
    public Chromosome(double[] values) {
        this.values = values;
    }

    /**
     * Randomizes the values of this chromosome.
     */
    public void randomize() {
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextDouble();
        }
    }

    /**
     * Randomizes the values of this chromosome in the given range.
     */
    public void randomize(int lower, int upper) {
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextDouble(lower, upper);
        }
    }

    /**
     * Returns a copy of this chromosome.
     *
     * @return a copy of this chromosome
     */
    public Chromosome copy() {
        return new Chromosome(Arrays.copyOf(values, values.length));
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
