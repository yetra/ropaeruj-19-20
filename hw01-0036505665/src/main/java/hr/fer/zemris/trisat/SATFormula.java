package hr.fer.zemris.trisat;

import java.util.Arrays;

/**
 * This class represents a formula in conjunctive normal form.
 *
 * @author Bruna Dujmović
 *
 */
public class SATFormula {

    /**
     * The number of variables in this formula.
     */
    private int numberOfVariables;

    /**
     * The clauses comprising this formula.
     */
    private Clause[] clauses;

    /**
     * Constructs a {@link SATFormula} of the given clauses and number of variables.
     *
     * @param numberOfVariables the number of variables in the formula
     * @param clauses the clauses comprising the formula
     */
    public SATFormula(int numberOfVariables, Clause[] clauses) {
        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
    }

    /**
     * Returns the number of variables in this formula.
     *
     * @return the number of variables in this formula
     */
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    /**
     * Returns the number of clauses in this formula.
     *
     * @return the number of clauses in this formula
     */
    public int getNumberOfClauses() {
        return clauses.length;
    }

    /**
     * Returns the clause that is on the specified index in this formula.
     *
     * @param index the index of the clause
     * @throws IllegalArgumentException if the given index is not in range [1, size]
     * @return the clause that is on the specified index in this formula
     */
    public Clause getClause(int index) {
        if (index < 1 || index > getNumberOfClauses()) {
            throw new IllegalArgumentException("Index must be in range [1, size]");
        }

        return clauses[index - 1];
    }

    /**
     * Returns {@code true} if the given bit assignment satisfies this formula.
     *
     * @param assignment the bit assignment to check
     * @return {@code true} if the given bit assignment satisfies this formula
     */
    public boolean isSatisfied(BitVector assignment) {
        for (Clause clause : clauses) {
            if (!clause.isSatisfied(assignment)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(clauses).replace(", ", "").replace("[[", "[").replace("]]", "]");
    }
}
