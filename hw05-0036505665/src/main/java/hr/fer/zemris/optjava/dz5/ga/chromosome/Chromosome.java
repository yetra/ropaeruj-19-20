package hr.fer.zemris.optjava.dz5.ga.chromosome;

import java.util.Arrays;

/**
 * This class represents a chromosome to be used in various genetic algorithm implementations.
 *
 * @param <T> the type of the chromosome's values
 * @author Bruna Dujmović
 *
 */
public abstract class Chromosome<T> implements Comparable<Chromosome<T>> {

    /**
     * The values of the chromosome.
     */
    public T[] values;

    /**
     * The fitness of the chromosome.
     */
    public double fitness;

    /**
     * Randomizes the values of this chromosome.
     */
    public abstract void randomize();

    /**
     * Returns a copy of this chromosome.
     *
     * @return a copy of this chromosome
     */
    public abstract Chromosome<T> copy();

    /**
     * Calculates the fitness of this chromosome.
     */
    public abstract void calculateFitness();

    @Override
    public int compareTo(Chromosome<T> o) {
        return Double.compare(this.fitness, o.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome<?> that = (Chromosome<?>) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
