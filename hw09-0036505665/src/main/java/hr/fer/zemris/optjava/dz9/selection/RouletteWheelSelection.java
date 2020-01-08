package hr.fer.zemris.optjava.dz9.selection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of roulette wheel selection.
 *
 * @author Bruna Dujmović
 *
 */
public class RouletteWheelSelection implements Selection {

    @Override
    public double[][] from(double[][] population, double[] errors, int numberToSelect) {
        double[] cumulativeErrors = new double[population.length];

        cumulativeErrors[0] = adjust(errors[0]);
        for (int i = 1; i < population.length; i++) {
            cumulativeErrors[i] = cumulativeErrors[i - 1] + adjust(errors[i]);
        }

        double[][] parents = new double[numberToSelect][];
        for (int i = 0; i < numberToSelect; i++) {
            double randomError = ThreadLocalRandom.current().nextDouble() * cumulativeErrors[population.length - 1];

            int index = Arrays.binarySearch(cumulativeErrors, randomError);
            if (index < 0) {
                index = Math.abs(index + 1);
            }

            parents[i] = population[index];
        }

        return parents;
    }

    /**
     * Adjusts the given error value.
     *
     * @param error the error value to adjust
     * @return the adjusted error value
     */
    private double adjust(double error) {
        if (error != 0) {
            return 1 / error;
        }

        return Double.POSITIVE_INFINITY;
    }
}
