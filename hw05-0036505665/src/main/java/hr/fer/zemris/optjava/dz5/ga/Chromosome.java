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
     * Randomizes the values of this chromosome.
     */
    public void randomize() {
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextBoolean();
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
