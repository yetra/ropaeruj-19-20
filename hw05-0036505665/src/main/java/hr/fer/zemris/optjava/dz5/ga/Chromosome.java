package hr.fer.zemris.optjava.dz5.ga;

import hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a chromosome to be used in {@link GeneticAlgorithm}
 * that is based on a bit vector.
 *
 * @author Bruna Dujmović
 *
 */
public class Chromosome implements Comparable<Chromosome> {

    /**
     * The values of the chromosome.
     */
    public boolean[] values;

    /**
     * The fitness of the chromosome.
     */
    public double fitness;

    /**
     * Constructs a {@link Chromosome} of the specified size with all values set to 0 ({@code false}).
     *
     * @param size the size of the chromosome
     */
    public Chromosome(int size) {
        values = new boolean[size];
    }

    /**
     * Constructs a random {@link Chromosome} of the specified size if {@code randomize}
     * is set to {@code true}. Otherwise, all chromosome values will be set to 0 ({@code false}).
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
    public Chromosome(boolean[] values) {
        this.values = values;
    }

    /**
     * Randomizes the values of this chromosome.
     */
    public void randomize() {
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextBoolean();
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

    /**
     * Calculates the fitness of this chromosome.
     */
    public void calculateFitness() {
        int k = 0;

        for (boolean value : values) {
            if (value) {
                k++;
            }
        }

        if (k <= 0.8 * values.length) {
            fitness = (double) k / values.length;

        } else if (k <= 0.9 * values.length) {
            fitness = 0.8;

        } else {
            fitness = 2.0 * k / values.length - 1;
        }
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
