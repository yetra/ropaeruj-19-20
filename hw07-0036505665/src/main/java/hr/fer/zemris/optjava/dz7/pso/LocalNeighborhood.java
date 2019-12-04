package hr.fer.zemris.optjava.dz7.pso;

/**
 * Models a local neighborhood of a specified size.
 *
 * @author Bruna Dujmović
 *
 */
public class LocalNeighborhood implements Neighborhood {

    /**
     * The size of the neighborhood.
     */
    private int neighborhoodSize;

    /**
     * The number of dimensions of the function being optimized
     */
    private int dimensions;

    /**
     * {@code true} if minimization, {@code false} if maximization
     */
    private boolean minimize;

    /**
     * The best solution in the neighborhood of each particle.
     */
    private double[][] best;

    /**
     * Konstruktor.
     *
     * @param neighborhoodSize the size of the neighborhood
     * @param swarmSize the size of the particle swarm
     * @param dimensions the number of dimensions of the function being optimized
     * @param minimize {@code true} if minimization, {@code false} if maximization
     */
    public LocalNeighborhood(int neighborhoodSize, int swarmSize, int dimensions, boolean minimize) {
        this.neighborhoodSize = neighborhoodSize;
        this.dimensions = dimensions;
        this.minimize = minimize;
        this.best = new double[swarmSize][dimensions];
    }

    @Override
    public void scan(Particle[] swarm) {
        for (int index = 0; index < swarm.length; index++) {
            int start = Math.max(index - neighborhoodSize / 2, 0);
            int end = Math.min(index + neighborhoodSize / 2, swarm.length - 1);

            int bestIndex = start;
            double bestValue = swarm[bestIndex].bestValue;

            for (int i = start + 1; i <= end; i++) {
                if ((minimize && swarm[i].bestValue < bestValue)
                        || (!minimize && swarm[i].bestValue > bestValue)) {
                    bestValue = swarm[i].bestValue;
                    bestIndex = i;
                }
            }

            System.arraycopy(swarm[bestIndex].bestPosition, 0, best[index], 0, dimensions);
        }
    }

    @Override
    public double[] getBestFor(int index) {
        return best[index];
    }
}