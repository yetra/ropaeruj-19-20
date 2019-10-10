package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Random;

/**
 * This class is represents an iterated local search algorithm for solving the 3-SAT problem.
 *
 * Instead of stopping the search as in {@link Algorithm2} when the algorithm gets stuck in a local optimum,
 * a random number of randomly chosen variables are flipped in the assignment.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm6 extends Algorithm2 {

    /**
     * Constructs an {@link Algorithm6} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm6(SATFormula formula) {
        super(formula);
    }

    /**
     * Executes the algorithm on the given formula.
     *
     * @return a solution if found, else {@code null}
     */
    public BitVector execute() {
        Random rand = new Random();
        BitVector assignment = new BitVector(rand, formula.getNumberOfVariables());

        int t = 0;
        while (t < MAX_TRIES) {

            if (formula.isSatisfied(assignment)) {
                solution = assignment;
                break;
            }

            BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
            BitVector[] neighborhood = generator.createNeighborhood();
            BitVector[] bestNeighbors = bestOf(neighborhood);

            if (fit(bestNeighbors[0]) < fit(assignment)) {
                assignment = getRandomlyFlippedAssignment(rand, assignment);
                t++;
                continue;
            }

            int index = rand.nextInt(bestNeighbors.length);
            assignment = bestNeighbors[index];

            t++;
        }

        return solution;
    }

    /**
     * Returns an assignment in which a random number of randomly chosen variables are flipped.
     *
     * @param rand the generator of random numbers
     * @param assignment the assignment whose variable should be flipped
     * @return an assignment in which a random number of randomly chosen variables are flipped
     */
    private BitVector getRandomlyFlippedAssignment(Random rand, BitVector assignment) {
        MutableBitVector mutableAssignment = assignment.copy();

        int numberToFlip = rand.nextInt(formula.getNumberOfVariables() + 1);

        for (int i = 0; i < numberToFlip; i++) {
            int index = rand.nextInt(assignment.getSize()) + 1;
            mutableAssignment.set(index, !assignment.get(index));
        }

        return mutableAssignment;
    }
}
