package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.function.Function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DE {

    private Function function;

    private int dimensions;

    private int populationSize;

    private int maxIterations;

    private double errorThreshold;

    private double differentialWeight;

    private double crossoverProbability;

    private double[] lowerBounds;

    private double[] upperBounds;

    private double[] best;

    private double bestError;

    private double[] errors;

    public DE(Function function, int dimensions, int populationSize, int maxIterations, double errorThreshold,
              double differentialWeight, double crossoverProbability, double[] lowerBounds, double[] upperBounds) {
        this.function = function;
        this.dimensions = dimensions;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.errorThreshold = errorThreshold;
        this.differentialWeight = differentialWeight;
        this.crossoverProbability = crossoverProbability;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    public double[] run() {
        double[][] vectors = new double[populationSize][dimensions];
        initialize(vectors);
        evaluate(vectors);

        double[] mutant_vector = new double[dimensions];
        double[][] trial_vectors = new double[populationSize][dimensions];

        int iteration = 0;
        while (iteration < maxIterations && bestError > errorThreshold) {
            for (int i = 0; i < populationSize; i++) {
                int r0, r1, r2;
                do {
                    r0 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r0 == i);
                do {
                    r1 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r1 == i || r1 == r0);
                do {
                    r2 = ThreadLocalRandom.current().nextInt(populationSize);
                } while (r2 == r1 || r2 == r0 || r2 == i);

                double r = ThreadLocalRandom.current().nextDouble(dimensions);

                for (int j = 0; j < dimensions; j++) {
                    mutant_vector[j] = vectors[r0][j] + differentialWeight * (vectors[r1][j] - vectors[r2][j]);
                }

                for (int j = 0; j < dimensions; j++) {
                    double randomNumber = ThreadLocalRandom.current().nextDouble();

                    if (randomNumber <= crossoverProbability || j == r) {
                        trial_vectors[i][j] = mutant_vector[j];
                    } else {
                        trial_vectors[i][j] = vectors[i][j];
                    }
                }
            }

            for (int i = 0; i < populationSize; i++) {
                double trial_value = function.valueAt(trial_vectors[i]);

                if (trial_value < errors[i]) {
                    vectors[i] = trial_vectors[i];
                    errors[i] = trial_value;

                    if (errors[i] < bestError) {
                        best = Arrays.copyOf(vectors[i], vectors[i].length);
                        bestError = errors[i];
                    }
                }
            }

            System.out.println(Arrays.toString(best) + " - " + bestError);
        }

        return best;
    }

    private void initialize(double[][] vectors) {
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < dimensions; j++) {
                vectors[i][j] = ThreadLocalRandom.current().nextDouble(lowerBounds[i], upperBounds[i]);
            }
        }
    }

    private void evaluate(double[][] vectors) {
        if (errors == null) {
            errors = new double[populationSize];
        }

        for (int i = 0; i < populationSize; i++) {
            errors[i] = function.valueAt(vectors[i]);

            if (best == null || errors[i] < bestError) {
                best = Arrays.copyOf(vectors[i], vectors[i].length);
                bestError = errors[i];
            }
        }
    }
}
