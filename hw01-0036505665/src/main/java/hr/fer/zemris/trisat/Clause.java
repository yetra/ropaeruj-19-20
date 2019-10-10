package hr.fer.zemris.trisat;

import java.util.Arrays;

/**
 * This class represents a sum of literals (variables or their complements) of a formula in conjunctive normal form.
 *
 * @author Bruna Dujmović
 *
 */
public class Clause {

    /**
     * An array of this clause's literals represented by their indexes.
     * Negative indexes represent variable complements.
     */
    private int[] literals;

    /**
     * Constructs a clause containing literals specified by the given indexes.
     *
     * @param indexes the indexes of literals in this clause
     */
    public Clause(int[] indexes) {
        literals = indexes;
    }

    /**
     * Returns the number of literals in this clause.
     *
     * @return the number of literals in this clause
     */
    public int getSize() {
        return literals.length;
    }

    /**
     * Returns the index of the literal that is on the index-th position in this clause.
     *
     * @param index the position of the literal
     * @throws IllegalArgumentException if the given index is not in range [1, size]
     * @return the index of the literal that is on the index-th position in this clause
     */
    public int getLiteral(int index) {
        if (index < 1 || index > getSize()) {
            throw new IllegalArgumentException("Index must be in range [1, size]");
        }

        return literals[index - 1];
    }

    /**
     * Returns {@code true} if the given bit assignment satisfies this clause.
     *
     * @param assignment the bit assignment to check
     * @return {@code true} if the given bit assignment satisfies this clause
     */
    public boolean isSatisfied(BitVector assignment) {
        for (int index : literals) {
            boolean bit = assignment.get(Math.abs(index));

            if ((index > 0) == bit) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(literals).replace(", ", " + ");
    }
}
