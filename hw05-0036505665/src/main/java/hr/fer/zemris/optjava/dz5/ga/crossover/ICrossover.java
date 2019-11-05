package hr.fer.zemris.optjava.dz5.ga.crossover;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Collection;

/**
 * An interface to be implemented by different types of GA crossover.
 * Each implementation should provide a method that returns a collection
 * of child chromosomes obtained by crossing two parent chromosomes.
 *
 * @param <T> the type of the chromosome's values
 * @author Bruna Dujmović
 *
 */
public interface ICrossover<T> {

    /**
     * Performs the crossover on two given parent chromosomes.
     *
     * @param firstParent the first parent
     * @param secondParent the second parent
     * @return a collection of child chromosomes obtained by crossing the given parents
     * @throws IllegalArgumentException if the parent chromosomes are not the same size
     */
    Collection<Chromosome<T>> of(Chromosome<T> firstParent, Chromosome<T> secondParent);
}
