package hr.fer.zemris.optjava.dz10.selection;

/**
 * An interface to be implemented by different types of GA hr.fer.zemris.optjava.dz10.selection.
 * Each implementation should provide a method for selecting a specified amount of
 * solutions from a given population.
 *
 * @author Bruna Dujmović
 *
 */
public interface Selection {

    /**
     * Selects a specified amount of from the given population.
     *
     *
     * @param population the population to select from
     * @param populationFitness the fitness values for each solution in the population
     * @param numberToSelect the number of solutions to select
     * @return the selected solutions
     */
    double[][] from(double[][] population, double[] populationFitness, int numberToSelect);
}
