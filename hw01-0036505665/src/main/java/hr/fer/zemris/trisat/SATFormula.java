package hr.fer.zemris.trisat;

/**
 * This class represents a formula in conjunctive normal form.
 *
 * @author Bruna Dujmović
 *
 */
public class SATFormula {

    /**
     * Constructs a {@link SATFormula} of the given clauses and number of variables.
     *
     * @param numberOfVariables the number of variables in the formula
     * @param clauses the clauses comprising the formula
     */
    public SATFormula(int numberOfVariables, Clause[] clauses) {

    }

    /**
     * Returns the number of variables in this formula.
     *
     * @return the number of variables in this formula
     */
    public int getNumberOfVariables() {
        return -1;
    }

    /**
     * Returns the number of clauses in this formula.
     *
     * @return the number of clauses in this formula
     */
    public int getNumberOfClauses() {
        return -1;
    }

    /**
     * Returns the clause that is on the specified index in this formula.
     *
     * @param index the index of the clause
     * @return the clause that is on the specified index in this formula
     */
    public Clause getClause(int index) {
        return null;
    }

    /**
     * Returns {@code true} if the given bit assignment satisfies this formula.
     *
     * @param assignment the bit assignment to check
     * @return if the given bit assignment satisfies this formula
     */
    public boolean isSatisfied(BitVector assignment) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
