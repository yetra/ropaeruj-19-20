package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * The base class that represents an algorithm for solving the 3-SAT problem.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class Algorithm {

    /**
     * The formula whose solution should be found.
     */
    SATFormula formula;

    /**
     * The solution of the 3-SAT problem.
     */
    BitVector solution;

    /**
     * Executes the algorithm on the given formula.
     *
     * @return a solution if found, else {@code null}
     */
    public abstract BitVector execute();
}
