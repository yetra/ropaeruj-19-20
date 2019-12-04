package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.function.Function;

public class PSO {

    /**
     * The minimum and final value of the inertia weight.
     */
    private static final double WEIGHT_MIN = 0.4;

    /**
     * The maximum and initial value of the inertia weight.
     */
    private static final double WEIGHT_MAX = 0.9;

    /**
     * The function to optimize.
     */
    private Function function;

    /**
     * The number of dimensions of {@link #function}.
     */
    private int dimensions;

    /**
     * The {@link Neighborhood} implementation to use for finding the social best solution for a given particle.
     */
    private Neighborhood neighborhood;

    /**
     * {@code true} if the specified {@link #function} should be minimized, {@code false} if it should be maximized
     */
    private boolean minimize;

    /**
     * The lowest allowed value for each position vector component.
     */
    private double[] mins;

    /**
     * The highest allowed value for each position vector component.
     */
    private double[] maxs;

    /**
     * The bounds for velocity vector components.
     */
    private double[] velocityBounds;

    /**
     * The number of particles in the population (swarm).
     */
    private int swarmSize;

    /**
     * The maximum number of algorithm iterations.
     */
    private int maxIterations;

    /**
     * The iteration in which the inertia weight becomes constant and equal to {@link #WEIGHT_MAX}.
     * Set to {@code maxIterations / 2} in the constructor.
     */
    private int linearWeightThreshold;

    /**
     * The individual factor used in calculating a particle's position and velocity.
     */
    private double c1;

    /**
     * The social factor used in calculating a particle's position and velocity.
     */
    private double c2;

    /**
     * Constructs a {@link PSO} instance of the given parameters.
     *
     * @param function the function to optimize
     * @param neighborhood the object used for finding the social best solution for a given particle
     * @param minimize {@code true} minimization, {@code false} if maximization
     * @param mins the lowest allowed value for each position vector component
     * @param maxs the highest allowed value for each position vector component
     * @param velocityBoundsPercentage the percentage of the search space used for calculating {@link #velocityBounds}
     * @param swarmSize the number of particles in the population (swarm)
     * @param maxIterations the maximum number of algorithm iterations
     * @param c1 the individual factor used in calculating a particle's position and velocity
     * @param c2 the social factor used in calculating a particle's position and velocity
     */
    public PSO(Function function, Neighborhood neighborhood, boolean minimize, double[] mins, double[] maxs,
               double velocityBoundsPercentage, int swarmSize, int maxIterations, double c1, double c2) {
        this.function = function;
        this.dimensions = function.getDimensions();
        this.neighborhood = neighborhood;
        this.minimize = minimize;

        this.mins = mins;
        this.maxs = maxs;
        this.velocityBounds = new double[dimensions];
        for(int d = 0; d < dimensions; d++) {
            velocityBounds[d] = (maxs[d] - mins[d]) * velocityBoundsPercentage;
        }

        this.swarmSize = swarmSize;
        this.maxIterations = maxIterations;
        this.linearWeightThreshold = maxIterations / 2;
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * Initializes the given swarm with {@link #swarmSize} particles of random positions and velocities.
     *
     * @param swarm the swarm to initialize.
     */
    private void initialize(Particle[] swarm) {
        for(int i = 0; i < swarmSize; i++) {
            swarm[i] = new Particle(dimensions, mins, maxs, velocityBounds);
        }
    }
}
