package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is represents a RandomWalkSAT algorithm for solving the 3-SAT problem.
 *
 * In each iteration, a random unsatisfied clause is selected and used to flip a variable of the current assignment.
 * The variable to flip is chosen so that the number of unsatisfied clauses in the new assignment is minimal.
 * Depending on {@link #RANDOM_FLIP_PROBABILITY}, a random variable might be flipped instead.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm5 extends Algorithm2 {

    /**
     * The maximum number of variable flips to perform on a solution.
     */
    private static final int MAX_FLIPS = 10;

    /**
     * The probability of a random variable flip.
     */
    private static final double RANDOM_FLIP_PROBABILITY = 0.1;

    /**
     * Constructs an {@link Algorithm5} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm5(SATFormula formula) {
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

                Clause clause = getRandomUnsatisfiedClause(rand, assignment);

                if (rand.nextDouble() < RANDOM_FLIP_PROBABILITY) {
                    assignment = getRandomlyFlippedAssignment(rand, clause, assignment);

                } else {
                    assignment = getBestFlippedAssignment(clause, assignment);
                }
            }
        }

        return null;
    }

    /**
     * Returns a random unsatisfied clause for a given assignment.
     *
     * @param rand the random number generator
     * @param assignment the assignment to be satisfied
     * @return a random unsatisfied clause for a given assignment
     */
    private Clause getRandomUnsatisfiedClause(Random rand, BitVector assignment) {
        List<Clause> unsatisfiedClauses = new ArrayList<>();
        int numberOfClauses = formula.getNumberOfClauses();

        for (int i = 0; i < numberOfClauses; i++) {
            Clause clause = formula.getClause(i);

            if (!clause.isSatisfied(assignment)) {
                unsatisfiedClauses.add(clause);
            }
        }

        int index = rand.nextInt(unsatisfiedClauses.size());
        return unsatisfiedClauses.get(index);
    }

    /**
     * Returns an assignment obtained by randomly flipping a variable using a given clause's literals.
     *
     * @param clause the clause that determines which variable to flip
     * @param assignment the assignment whose variable should be flipped
     * @return an assignment obtained by randomly flipping a variable using a given clause's literals
     */
    private BitVector getRandomlyFlippedAssignment(Random rand, Clause clause, BitVector assignment) {
        int index = rand.nextInt(clause.getSize());
        int literal = clause.getLiteral(index);
        int indexToFlip = Math.abs(literal);

        MutableBitVector mutableAssignment = assignment.copy();
        mutableAssignment.set(indexToFlip, !assignment.get(indexToFlip));

        return mutableAssignment;
    }

    /**
     * Returns the best assignment obtained by flipping variables using a given clause's literals.
     *
     * @param clause the clause that determines which variables to flip
     * @param assignment the assignment whose variables should be flipped
     * @return the best assignment obtained by flipping variables using a given clause's literals
     */
    private BitVector getBestFlippedAssignment(Clause clause, BitVector assignment) {
        int size = clause.getSize();
        BitVector[] flippedAssignments = new BitVector[size];

        for (int i = 0; i < size; i++) {
            MutableBitVector mutableAssignment = assignment.copy();

            int literal = clause.getLiteral(i);
            int indexToFlip = Math.abs(literal);

            mutableAssignment.set(indexToFlip, !assignment.get(indexToFlip));
            flippedAssignments[i] = mutableAssignment;
        }

        return bestOf(flippedAssignments)[0];
    }
}
