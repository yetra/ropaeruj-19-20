package hr.fer.zemris.generic.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.Collection;

/**
 * An interface to be implemented by different types of GA selection.
 * Each implementation should provide a method for selecting a single
 * solution from a given population.
 *
 * @param <T> the type of the solution's values
 * @author Bruna Dujmović
 *
 */
public interface ISelection<T> {

    /**
     * Selects a single solution from the given population.
     *
     *
     * @param population the population to select from
     * @return the selected solution
     */
    GASolution<T> from(Collection<GASolution<T>> population);
}
