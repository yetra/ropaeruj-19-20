package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.function.Function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DE {

    private int D;

    private int n;

    private double[] lowerBounds;
    private double[] upperBounds;

    private Function function;

    private int maxIterations;

    private double[] best;

    private double bestError = -1;

    private double minError;

    private double[] values;

    private double F;

    private double Cr;

    public DE(int d, int n, double[] lowerBounds, double[] upperBounds, Function function, int maxIterations,
              double f, double cr) {
        D = d;
        this.n = n;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
        this.function = function;
        this.maxIterations = maxIterations;
        F = f;
        Cr = cr;
    }

    public void run() {
        double[][] vectors = new double[n][D];
        initialize(vectors);
        evaluate(vectors);

        double[] mutant_vector = new double[D];
        double[][] trial_vectors = new double[n][D];

        int iteration = 0;
        while (iteration < maxIterations && bestError > minError) {
            for (int i = 0; i < n; i++) {
                int r0, r1, r2;
                do {
                    r0 = ThreadLocalRandom.current().nextInt(n);
                } while (r0 == i);
                do {
                    r1 = ThreadLocalRandom.current().nextInt(n);
                } while (r1 == i || r1 == r0);
                do {
                    r2 = ThreadLocalRandom.current().nextInt(n);
                } while (r2 == r1 || r2 == r0 || r2 == i);

                double jrand = ThreadLocalRandom.current().nextDouble(D);

                for (int j = 0; j < D; j++) {
                    mutant_vector[j] = vectors[r0][j] + F * (vectors[r1][j] - vectors[r2][j]);
                }

                for (int j = 0; j < D; j++) {
                    double randomNumber = ThreadLocalRandom.current().nextDouble();

                    if (randomNumber <= Cr || j == jrand) {
                        trial_vectors[i][j] = mutant_vector[j];
                    } else {
                        trial_vectors[i][j] = vectors[i][j];
                    }
                }
            }

            for (int i = 0; i < D; i++) {
                double trial_value = function.valueAt(trial_vectors[i]);

                if (trial_value < values[i]) {
                    vectors[i] = trial_vectors[i];
                    values[i] = trial_value;

                    if (values[i] < minError) {
                        best = Arrays.copyOf(vectors[i], vectors[i].length);
                        minError = values[i];
                    }
                }
            }
        }
    }

    private void initialize(double[][] vectors) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < D; j++) {
                vectors[i][j] = ThreadLocalRandom.current().nextDouble(lowerBounds[i], upperBounds[i]);
            }
        }
    }

    private void evaluate(double[][] vectors) {
        if (values == null) {
            values = new double[n];
        }

        for (int i = 0; i < n; i++) {
            values[i] = function.valueAt(vectors[i]);

            if (best == null || values[i] < minError) {
                best = Arrays.copyOf(vectors[i], vectors[i].length);
                minError = values[i];
            }
        }
    }
}
