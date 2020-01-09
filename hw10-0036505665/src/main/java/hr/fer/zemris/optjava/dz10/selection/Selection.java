package hr.fer.zemris.optjava.dz10.selection;

import hr.fer.zemris.optjava.dz10.Solution;

/**
 * An interface to be implemented by different types of GA selection.
 *
 * Each implementation should provide a method for selecting a specified amount of
 * solutions from a given population.
 *
 * @author Bruna Dujmović
 *
 */
public interface Selection {

    /**
     * Selects a specified amount of solutions from the given population.
     *
     * @param population the population to select from
     * @param numberToSelect the number of solutions to select
     * @return an array containing the selected solutions
     */
    Solution[] from(Solution[] population, int numberToSelect);
}
