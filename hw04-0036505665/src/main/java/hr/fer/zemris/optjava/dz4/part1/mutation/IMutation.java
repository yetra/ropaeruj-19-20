package hr.fer.zemris.optjava.dz4.part1.mutation;

import hr.fer.zemris.optjava.dz4.part1.Chromosome;

/**
 * An interface to be implemented by different types of GA mutation.
 * Each implementation should provide a method for mutating a signle chromosome.
 *
 * @author Bruna Dujmović
 *
 */
public interface IMutation {

    /**
     * Returns a mutated copy of the given chromosome.
     *
     * @param chromosome the chromosome to mutate
     * @return a mutated copy of the given chromosome
     */
    Chromosome of(Chromosome chromosome);

    /**
     * Mutates the given chromosome.
     *
     * @param chromosome the chromosome to mutate
     */
    void mutate(Chromosome chromosome);
}
