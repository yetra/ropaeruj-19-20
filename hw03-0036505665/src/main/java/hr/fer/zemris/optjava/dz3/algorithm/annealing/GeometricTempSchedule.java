package hr.fer.zemris.optjava.dz3.algorithm.annealing;

/**
 * A geometric temperature schedule where that calculates the next temperature using the formula
 *
 *  T_k = alpha^k * T_0,
 *
 * where k = iteration count, T_0 = initial temperature, T_k = temperature in iteration k.
 *
 *
 * @author Bruna Dujmović
 *
 */
public class GeometricTempSchedule implements ITempSchedule {

    /**
     * The base of the exponential factor.
     */
    private double alpha;

    /**
     * The initial temperature.
     */
    private double initialTemperature;

    /**
     * The current temperature.
     */
    private double currentTemperatrue;

    /**
     * The maximum number of iterations for the inner loop.
     */
    private int innerLoopCount;

    /**
     * The maximum number of iterations for the outer loop.
     */
    private int outerLoopCount;

    /**
     * Constructs a {@link GeometricTempSchedule} of the given parameters.
     *
     * @param alpha the base of the exponential factor
     * @param initialTemperature the initial temperature
     * @param innerLoopCount the maximum number of iterations for the inner loop
     * @param outerLoopCount the maximum number of iterations for the outer loop
     */
    public GeometricTempSchedule(double alpha, double initialTemperature, int innerLoopCount, int outerLoopCount) {
        this.alpha = alpha;

        this.initialTemperature = initialTemperature;
        this.currentTemperatrue = initialTemperature;

        this.innerLoopCount = innerLoopCount;
        this.outerLoopCount = outerLoopCount;
    }

    @Override
    public double getNextTemperature() {
        double temperature = currentTemperatrue;
        currentTemperatrue *= alpha;

        return temperature;
    }

    @Override
    public int getInnerLoopCount() {
        return innerLoopCount;
    }

    @Override
    public int getOuterLoopCount() {
        return outerLoopCount;
    }

}
