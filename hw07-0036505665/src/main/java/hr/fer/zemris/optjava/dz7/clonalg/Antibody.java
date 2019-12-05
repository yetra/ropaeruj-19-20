package hr.fer.zemris.optjava.dz7.clonalg;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Models a {@link CLONALG} antibody.
 *
 * @author Bruna Dujmović
 * 
 */
public class Antibody implements Comparable<Antibody> {

    /**
     * The variables of this antibody.
     */
    double[] variables;

    /**
     * The value (fitness/error) of this antibody.
     */
    double affinity;

    /**
     * Constructs an {@link Antibody} of the given dimensions with all its variables set to zero.
     *
     * @param dimensions the dimensions of the solution
     */
    public Antibody(int dimensions) {
        variables = new double[dimensions];
    }

    /**
     * Constructs an {@link Antibody} of the given dimensions with random variable values.
     *
     * @param dimensions the dimensions of the solution
     * @param mins the lowest allowed value for each antibody variable
     * @param maxs the highest allowed value for each antibody variable
     */
    public Antibody(int dimensions, double[] mins, double[] maxs) {
        this(dimensions);

        randomize(mins, maxs);
    }

    /**
     * Constructs an {@link Antibody} of the given variables and value.
     *
     * @param variables the variables of this antibody
     * @param affinity the value (fitness/error) of this antibody
     */
    private Antibody(double[] variables, double affinity) {
        this.variables = variables;
        this.affinity = affinity;
    }

    /**
     * Randomizes the variables of this antibody.
     *
     * @param mins the lowest allowed value for each antibody variable
     * @param maxs the highest allowed value for each antibody variable
     */
    public void randomize(double[] mins, double[] maxs) {
        for (int i = 0; i < variables.length; i++) {
            variables[i] = ThreadLocalRandom.current().nextDouble(mins[i], maxs[i]);
        }
    }

    /**
     * Returns a deep copy of this antibody.
     *
     * @return a deep copy of this antibody
     */
    public Antibody copy() {
        return new Antibody(Arrays.copyOf(variables, variables.length), affinity);
    }

    @Override
    public int compareTo(Antibody o) {
        return Double.compare(affinity, o.affinity);
    }
}
