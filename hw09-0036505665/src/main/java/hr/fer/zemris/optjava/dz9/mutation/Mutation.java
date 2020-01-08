package hr.fer.zemris.optjava.dz9.mutation;

/**
 * An interface to be implemented by different types of GA mutation.
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
    void mutate(double[][] solutions);
}
