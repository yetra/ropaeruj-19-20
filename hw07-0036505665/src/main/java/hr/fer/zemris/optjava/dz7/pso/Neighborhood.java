package hr.fer.zemris.optjava.dz7.pso;

/**
 * An interface modeling the neighborhood of a swarm {@link Particle}.
 *
 * @author Bruna Dujmović
 *
 */
public interface Neighborhood {
    /**
     * Method to be used for updating best solution data stored in a {@link Neighborhood} object.
     * Must be called before {@link #getBestFor(int)} and any updates to the swarm particles.
     *
     * @param swarm the particle swarm (population)
     */
    void scan(Particle[] swarm);

    /**
     * Returns the best position (solution) found in the neighborhood of a particle represented by the given index.
     *
     * @param index the index of the particle
     * @return the best position found in the neighborhood of the specified particle
     */
    double[] getBestFor(int index);
}
