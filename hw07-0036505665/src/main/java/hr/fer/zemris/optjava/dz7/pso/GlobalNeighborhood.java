package hr.fer.zemris.optjava.dz7.pso;

/**
 * Models a global neighborhood which finds and stores the best solution for the entire particle swarm.
 *
 * @author Bruna Dujmović
 *
 */
public class GlobalNeighborhood implements Neighborhood {

    /**
     * The number of dimensions of the function being optimized
     */
    private int dimensions;

    /**
     * {@code true} if minimization, {@code false} if maximization
     */
    private boolean minimize;

    /**
     * The global best solution.
     */
    private double[] best;

    /**
     * Constructs a {@link GlobalNeighborhood}.
     *
     * @param dimensions the number of dimensions of the function being optimized
     * @param minimize {@code true} if minimization, {@code false} if maximization
     */
    public GlobalNeighborhood(int dimensions, boolean minimize) {
        this.dimensions = dimensions;
        this.minimize = minimize;
        best = new double[dimensions];
    }

    @Override
    public void scan(Particle[] swarm) {
        int bestIndex = 0;
        double bestValue = swarm[bestIndex].bestValue;

        for (int i = 1; i < swarm.length; i++) {
            if ((minimize && swarm[i].bestValue < bestValue)
                    || (!minimize && swarm[i].bestValue > bestValue)) {
                bestIndex = i;
                bestValue = swarm[i].bestValue;
            }
        }

        System.arraycopy(swarm[bestIndex].bestPosition, 0, best, 0, dimensions);
    }

    @Override
    public double[] getBestFor(int index) {
        return best;
    }
}
