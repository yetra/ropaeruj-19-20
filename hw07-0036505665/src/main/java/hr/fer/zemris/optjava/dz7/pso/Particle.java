package hr.fer.zemris.optjava.dz7.pso;

import java.util.concurrent.ThreadLocalRandom;

public class Particle {

    /**
     * The position vector (solution) of this particle.
     */
    double[] position;

    /**
     * The velocity vector of this particle.
     */
    double[] velocity;

    /**
     * The value of this particle at the current position - the fitness/error of the solution.
     */
    double value;

    /**
     * The personal best position (solution) that this particle has ever visited.
     */
    double[] bestPosition;

    /**
     * The value of this particle at its best position.
     */
    double bestValue;

    /**
     * Constructs a {@link Particle} of the given dimensions with all its vectors filled with zero.
     *
     * @param dimensions the dimensions of the solution
     */
    public Particle(int dimensions) {
        position = new double[dimensions];
        velocity = new double[dimensions];
        bestPosition = new double[dimensions];
    }

    /**
     * Constructs a {@link Particle} of the given dimensions with randomized vector values.
     *
     * @param dimensions the dimensions of the solution
     * @param mins the lowest allowed value for each {@link #position} vector component
     * @param maxs the highest allowed value for each {@link #position} vector component
     * @param velocityBounds the bounds for velocity vector components
     */
    public Particle(int dimensions, double[] mins, double[] maxs, double[] velocityBounds) {
        this(dimensions);

        randomize(dimensions, mins, maxs, velocityBounds);
        System.arraycopy(position, 0, bestPosition, 0, dimensions);
    }

    /**
     * Randomizes this particle's {@link #position} and {@link #velocity} vectors.
     *
     * @param dimensions the dimensions of the solution
     * @param mins the lowest allowed value for each {@link #position} vector component
     * @param maxs the highest allowed value for each {@link #position} vector component
     * @param velocityBounds the bounds for velocity vector components
     */
    public void randomize(int dimensions, double[] mins, double[] maxs, double[] velocityBounds) {
        for (int d = 0; d < dimensions; d++) {
            position[d] = ThreadLocalRandom.current().nextDouble() * (maxs[d] - mins[d]) + mins[d];
            velocity[d] = ThreadLocalRandom.current().nextDouble() * (2 * velocityBounds[d]) - velocityBounds[d];
        }
    }
}
