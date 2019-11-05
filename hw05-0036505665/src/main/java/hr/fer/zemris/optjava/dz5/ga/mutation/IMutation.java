package hr.fer.zemris.optjava.dz5.ga.mutation;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

/**
 * An interface to be implemented by different types of GA mutation.
 * Each implementation should provide a method for mutating a signle chromosome.
 *
 * @author Bruna Dujmović
 *
 */
public interface IMutation<T> {

    /**
     * Returns a mutated copy of the given chromosome.
     *
     * @param chromosome the chromosome to mutate
     * @return a mutated copy of the given chromosome
     */
    Chromosome<T> of(Chromosome<T> chromosome);

    /**
     * Mutates the given chromosome.
     *
     * @param chromosome the chromosome to mutate
     */
    void mutate(Chromosome<T> chromosome);
}
