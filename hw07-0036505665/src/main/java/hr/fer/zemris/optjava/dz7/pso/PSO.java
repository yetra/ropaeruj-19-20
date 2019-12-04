package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.function.Function;

import java.util.concurrent.ThreadLocalRandom;

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
     * Executes the algorithm.
     */
    public void run() {
        Particle[] swarm = new Particle[swarmSize];
        initialize(swarm);
        evaluate(swarm);

        int iteration = 0;
        while (iteration < maxIterations) {
            // linear inertia weight
            double weight;
            if (iteration < linearWeightThreshold) {
                weight = iteration * (WEIGHT_MIN - WEIGHT_MAX) / maxIterations + WEIGHT_MAX;
            } else {
                weight = WEIGHT_MIN;
            }

            neighborhood.scan(swarm);

            update(swarm, weight);
            evaluate(swarm);

            iteration++;
        }
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

    /**
     * Updates the positions and velocities of all particles in the given swarm.
     *
     * @param swarm the swarm to update
     * @param w the inertia weight used for calculating the new velocities
     */
    private void update(Particle[] swarm, double w) {
        for(int i = 0; i < swarm.length; i++) {
            double[] neighborhoodBest = neighborhood.getBestFor(i);

            for (int d = 0; d < dimensions; d++) {
                swarm[i].velocity[d] = w * swarm[i].velocity[d]
                        + c1 * ThreadLocalRandom.current().nextDouble()
                        * (swarm[i].bestPosition[d] - swarm[i].position[d])
                        + c2 * ThreadLocalRandom.current().nextDouble()
                        * (neighborhoodBest[d] - swarm[i].position[d]);

                if (swarm[i].velocity[d] < -velocityBounds[d]) {
                    swarm[i].velocity[d] = -velocityBounds[d];

                } else if (swarm[i].velocity[d] > velocityBounds[d]) {
                    swarm[i].velocity[d] = velocityBounds[d];
                }

                swarm[i].position[d] = swarm[i].position[d] + swarm[i].velocity[d];
            }
        }
    }

    /**
     * Evaluates the particles in the given swarm and updates their personal best, if needed.
     *
     * @param swarm the swarm to evaluate
     */
    private void evaluate(Particle[] swarm) {
        for (Particle particle : swarm) {
            particle.value = function.valueAt(particle.position);

            if ((minimize && particle.value < particle.bestValue)
                    || (!minimize && particle.value > particle.bestValue)) {
                particle.bestValue = particle.value;

                System.arraycopy(particle.position, 0, particle.bestPosition, 0, dimensions);
            }
        }
    }
}