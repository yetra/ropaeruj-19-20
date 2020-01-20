package hr.fer.zemris.generic.ga;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.Collection;

/**
 * An interface to be implemented by different types of GA crossover.
 * Each implementation should provide a method that returns a collection
 * of child solutions obtained by crossing two parent solutions.
 *
 * @param <T> the type of the solution's values
 * @author Bruna Dujmović
 *
 */
public interface ICrossover<T> {

    /**
     * Performs the crossover on two given parent solutions.
     *
     * @param firstParent the first parent
     * @param secondParent the second parent
     * @return a collection of child solutions obtained by crossing the given parents
     * @throws IllegalArgumentException if the parent solutions are not the same size
     */
    Collection<GASolution<T>> of(GASolution<T> firstParent, GASolution<T> secondParent);
}
