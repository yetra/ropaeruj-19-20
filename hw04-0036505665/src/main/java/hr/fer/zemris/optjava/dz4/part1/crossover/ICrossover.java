package hr.fer.zemris.optjava.dz4.part1.crossover;

import hr.fer.zemris.optjava.dz4.part1.Chromosome;

import java.util.Collection;

/**
 * An interface to be implemented by different types of GA crossover.
 * Each implementation should provide a method that returns a collection
 * of child chromosomes obtained by crossing two parent chromosomes.
 *
 * @author Bruna Dujmović
 *
 */
public interface ICrossover {

    /**
     * Performs the crossover on two given parent chromosomes.
     *
     * @param firstParent the first parent
     * @param secondParent the second parent
     * @return a collection of child chromosomes obtained by crossing the given parents
     * @throws IllegalArgumentException if the parent chromosomes are not the same size
     */
    Collection<Chromosome> of(Chromosome firstParent, Chromosome secondParent);
}
