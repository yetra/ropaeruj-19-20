package hr.fer.zemris.optjava.dz8.de.mutation;

/**
 * An implementation of DE mutation where the base vector is randomly selected from a given vector population.
 *
 * @author Bruna Dujmović
 *
 */
public class RandMutation implements Mutation {

    /**
     * The number of unique random indexes needed for generating a mutant vector.
     */
    private static final int RANDOM_INDEX_COUNT = 3;

    /**
     * F - the parameter determining the impact of the vector difference when constructing a mutant vector.
     */
    private double differentialWeight;

    /**
     * Constructs a {@link RandMutation}.
     *
     * @param differentialWeight F - the parameter determining the impact of the vector difference when constructing
     *                           a mutant vector
     */
    public RandMutation(double differentialWeight) {
        this.differentialWeight = differentialWeight;
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(vectors.length, currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[vectors[0].length];

        for (int j = 0; j < mutantVector.length; j++) {
            mutantVector[j] = vectors[indexes[0]][j]
                    + differentialWeight * (vectors[indexes[1]][j] - vectors[indexes[2]][j]);
        }

        return mutantVector;
    }
}
