package hr.fer.zemris.optjava.dz3.solution;

/**
 * The base class for representing a single solution of an optimization algorithm.
 */
public abstract class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

    /**
     * The fitness of this solution.
     */
    double fitness;

    /**
     * The value of this solution.
     */
    double value;

    @Override
    public int compareTo(SingleObjectiveSolution o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
