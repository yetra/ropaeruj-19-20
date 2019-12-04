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
}
