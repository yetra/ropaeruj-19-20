package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Random;

/**
 * This class is represents a GSAT algorithm for solving the 3-SAT problem.
 *
 * In each iteration, up to {@link #MAX_FLIPS} variables are altered in the
 * current solution so that the number of unsatisfied clauses is minimized.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm4 extends Algorithm2 {

    /**
     * The maximum number of variable flips to perform on a solution.
     */
    private static final int MAX_FLIPS = 10;

    /**
     * Constructs an {@link Algorithm4} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm4(SATFormula formula) {
        super(formula);
    }

    /**
     * Executes the algorithm on the given formula.
     *
     * @return a solution if found, else {@code null}
     */
    public BitVector execute() {
        Random rand = new Random();

        for (int i = 1; i <= MAX_TRIES; i++) {
            BitVector assignment = new BitVector(rand, formula.getNumberOfVariables());

            for (int j = 1; j < MAX_FLIPS; j++) {
                if (formula.isSatisfied(assignment)) {
                    return assignment;
                }

                BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
                BitVector[] neighborhood = generator.createNeighborhood();
                BitVector[] bestNeighbors = bestOf(neighborhood);

                int index = rand.nextInt(bestNeighbors.length);
                assignment = bestNeighbors[index];
            }
        }

        return null;
    }
}
