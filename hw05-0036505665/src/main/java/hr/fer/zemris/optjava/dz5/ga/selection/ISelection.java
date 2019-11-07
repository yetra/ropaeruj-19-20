package hr.fer.zemris.optjava.dz5.ga.selection;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;

import java.util.Collection;

/**
 * An interface to be implemented by different types of GA selection.
 * Each implementation should provide a method for selecting a single
 * chromosome from a given population.
 *
 * @param <T> the type of the chromosome's values
 * @author Bruna Dujmović
 *
 */
public interface ISelection<T> {

    /**
     * Selects a single chromosome from the given population.
     *
     *
     * @param population the population to select from
     * @return the selected chromosome
     */
    Chromosome<T> from(Collection<Chromosome<T>> population);
}
