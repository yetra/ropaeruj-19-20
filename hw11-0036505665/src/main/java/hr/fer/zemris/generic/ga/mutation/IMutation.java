package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;

/**
 * An interface to be implemented by different types of GA mutation.
 * Each implementation should provide a method for mutating a single solution.
 *
 * @param <T> the type of the solution's values
 * @author Bruna Dujmović
 *
 */
public interface IMutation<T> {

    /**
     * Returns a mutated copy of the given solution.
     *
     * @param solution the solution to mutate
     * @return a mutated copy of the given solution
     */
    GASolution<T> of(GASolution<T> solution);

    /**
     * Mutates the given solution.
     *
     * @param solution the solution to mutate
     */
    void mutate(GASolution<T> solution);
}
