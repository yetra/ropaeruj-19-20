package hr.fer.zemris.optjava.dz10.selection;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * An interface to be implemented by different types of GA selection.
 *
 * Each implementation should provide a method for selecting a single solution from a given population.
 *
 * @author Bruna Dujmović
 *
 */
public interface Selection {

    /**
     * Selects a single solution from the given population.
     *
     * @param population the population to select from
     * @return the selected solution
     */
    Solution from(Solution[] population);
}
