package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a chromosome to be used in {@link GeneticAlgorithm} that is based on a bit vector.
 *
 * @author Bruna Dujmović
 *
 */
public class BitVectorChromosome extends Chromosome<Boolean> {

    /**
     * Constructs a {@link BitVectorChromosome} of the specified size with all values set to 0 ({@code false}).
     *
     * @param size the size of the chromosome
     */
    public BitVectorChromosome(int size) {
        values = new Boolean[size];
    }

    /**
     * Constructs a random {@link BitVectorChromosome} of the specified size if {@code randomize}
     * is set to {@code true}. Otherwise, all chromosome values will be set to 0 ({@code false}).
     *
     * @param size the size of the chromosome
     * @param randomize {@code true} if {@link #values} should be set to random values
     */
    public BitVectorChromosome(int size, boolean randomize) {
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
    public BitVectorChromosome(Boolean[] values) {
        this.values = values;
    }

    @Override
    public void randomize() {
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextBoolean();
        }
    }

    @Override
    public Chromosome<Boolean> copy() {
        return new BitVectorChromosome(Arrays.copyOf(values, values.length));
    }

    @Override
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (boolean value : values) {
            sb.append(value ? "1" : "0");
        }

        return sb.toString();
    }
}

