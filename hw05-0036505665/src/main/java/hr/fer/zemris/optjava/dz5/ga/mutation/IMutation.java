package hr.fer.zemris.optjava.dz5.ga.mutation;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;

/**
 * An interface to be implemented by different types of GA mutation.
 * Each implementation should provide a method for mutating a single chromosome.
 *
 * @param <T> the type of the chromosome's values
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
