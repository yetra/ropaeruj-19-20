package hr.fer.zemris.optjava.dz8.de.mutation;

/**
 * An implementation of DE mutation where the base vector is the best vector found so far by the algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class BestMutation implements Mutation {

    /**
     * The number of unique random indexes needed for generating a mutant vector.
     */
    private static final int RANDOM_INDEX_COUNT = 2;

    /**
     * F - the parameter determining the impact of the vector difference when constructing a mutant vector.
     */
    private double differentialWeight;

    /**
     * Constructs a {@link BestMutation}.
     *
     * @param differentialWeight F - the parameter determining the impact of the vector difference when constructing
     *                           a mutant vector
     */
    public BestMutation(double differentialWeight) {
        this.differentialWeight = differentialWeight;
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(vectors.length, currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[vectors[0].length];

        for (int j = 0; j < mutantVector.length; j++) {
            mutantVector[j] = best[j] + differentialWeight * (vectors[indexes[0]][j] - vectors[indexes[1]][j]);
        }

        return mutantVector;
    }
}
