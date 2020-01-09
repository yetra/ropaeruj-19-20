package hr.fer.zemris.optjava.dz10.mutation;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * An interface to be implemented by different types of GA mutation.
 *
 * Each implementation should provide a method for mutating an array of solutions.
 *
 * @author Bruna Dujmović
 *
 */
public interface Mutation {

    /**
     * Mutates the given solutions.
     *
     * @param solutions the solutions to mutate
     */
    void mutate(Solution[] solutions);
}
