package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * This class represents an exhaustive search algorithm for solving the 3-SAT problem.
 *
 * Possible solutions are generated from binary representations of integers
 * in range [0, 2^m], where m is the number of variables in the formula.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm1 extends Algorithm {

    /**
     * Constructs an {@link Algorithm1} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm1(SATFormula formula) {
        this.formula = formula;
    }

    /**
     * Executes the algorithm on the given formula.
     *
     * Returns a solution if found, otherwise returns {@code null}.
     * If multiple solutions are found, the first solution will be returned.
     *
     * All solutions are printed to the console.
     *
     * @return a solution if found, else {@code null}
     */
    public BitVector execute() {
        double maximumValue = Math.pow(2, formula.getNumberOfVariables());
        int currentValue = 0;

        while (currentValue < maximumValue) {
            BitVector assignment = new BitVector(currentValue, formula.getNumberOfVariables());

            if (formula.isSatisfied(assignment)) {
                if (solution == null) {
                    solution = assignment;
                }

                System.out.println(assignment);
            }

            currentValue++;
        }

        return solution;
    }
}
