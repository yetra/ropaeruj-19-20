package hr.fer.zemris.optjava.dz4.ga.selection;

import hr.fer.zemris.optjava.dz4.ga.Chromosome;

import java.util.List;

/**
 * An interface to be implemented by different types of GA selection.
 * Each implementation should provide a method for selecting a single
 * chromosome from a given population.
 *
 * @author Bruna Dujmović
 *
 */
public interface ISelection {

    /**
     * Selects a single chromosome from the given population.
     *
     *
     * @param population the population to select from
     * @return the selected chromosome
     */
    Chromosome from(List<Chromosome> population);
}
