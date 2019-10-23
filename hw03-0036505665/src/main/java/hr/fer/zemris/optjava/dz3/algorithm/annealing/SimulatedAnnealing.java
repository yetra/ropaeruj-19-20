package hr.fer.zemris.optjava.dz3.algorithm.annealing;

import hr.fer.zemris.optjava.dz3.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of the simulated annealing optimization algorithm.
 *
 * @param <T> the type of solutions to use
 */
public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {

    /**
     * The decoder for decoding solutions.
     */
    private IDecoder<T> decoder;

    /**
     * The generator of neighboring solutions.
     */
    private INeighborhood<T> neighborhood;

    /**
     * The initial solution.
     */
    private T startWith;

    /**
     * The function being optimised.
     */
    private IFunction function;

    /**
     * The temperature change schedule for the algorithm.
     */
    private ITempSchedule tempSchedule;

    /**
     * {@code true} for function minimization, {@code false} for maximisation
     */
    private boolean minimize;

    /**
     * Constructs a {@link SimulatedAnnealing} object of the given parameters.
     *
     * @param decoder the decoder for decoding solutions
     * @param neighborhood the generator of neighboring solutions
     * @param startWith the initial solution
     * @param function the function being optimised
     * @param tempSchedule the temperature change schedule for the algorithm
     * @param minimize {@code true} for function minimization, {@code false} for maximisation
     */
    public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith,
                              IFunction function, ITempSchedule tempSchedule, boolean minimize) {
        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.tempSchedule = tempSchedule;
        this.minimize = minimize;
    }

    @Override
    public void run() {
        int innerLoopCount = tempSchedule.getInnerLoopCount();
        int outerLoopCount = tempSchedule.getOuterLoopCount();

        T solution = startWith;

        for (int i = 0; i < outerLoopCount; i++) {
            double temperature = tempSchedule.getNextTemperature();

            for (int j = 0; j < innerLoopCount; j++) {
                T neighbor = neighborhood.randomNeighbor(solution);

                double[] newPoint = decoder.decode(neighbor);
                double[] point = decoder.decode(solution);

                double delta = function.valueAt(point) - function.valueAt(newPoint);
                if (minimize) {
                    delta = -delta;
                }

                if (delta <= 0 || ThreadLocalRandom.current().nextDouble() < Math.exp(-delta/temperature)) {
                    solution = neighbor;
                }
            }
        }
    }
}
