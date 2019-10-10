package hr.fer.zemris.trisat;

/**
 * A helper class for obtaining and maintaining statistics used in {@link hr.fer.zemris.trisat.algorithms.Algorithm3}.
 *
 * @author Bruna Dujmović
 *
 */
public class SATFormulaStats {

    /**
     * Constants used in calculations.
     */
    private static final double PERCENTAGE_CONSTANT_UP = 0.01;
    private static final double PERCENTAGE_CONSTANT_DOWN = 0.1;
    private static final double PERCENTAGE_UNIT_AMOUNT = 50;

    /**
     * The formula to collect statistics for.
     */
    private SATFormula formula;

    /**
     * The assignment to analyze.
     */
    private BitVector assignment;

    /**
     * An array of clauses' percentages.
     */
    private double[] percentages;

    /**
     * The number of satisfied clauses.
     */
    private int numberOfSatisfied;

    /**
     * The percentage bonus.
     */
    private double percentageBonus;

    /**
     * Constructs a {@link SATFormulaStats} object for the given formula.
     *
     * @param formula the formula to collect statistics for
     */
    public SATFormulaStats(SATFormula formula) {
        this.formula = formula;
        this.percentages = new double[formula.getNumberOfClauses()];
    }

    /**
     * Analyzes the given assignment and updates statistical data accordingly.
     *
     * @param assignment the assignment to analyze
     * @param updatePercentages {@code true} if the {@link #percentages} array should be updated,
     *                          {@code false} if {@link #percentageBonus} should be updated
     */
    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        this.assignment = assignment;
        numberOfSatisfied = 0;

        if (updatePercentages) {
            for (int i = 0, size = formula.getNumberOfClauses(); i < size; i++) {
                if (formula.getClause(i).isSatisfied(assignment)) {
                    percentages[i] += (1 - percentages[i]) * PERCENTAGE_CONSTANT_UP;
                    numberOfSatisfied++;

                } else {
                    percentages[i] += (0 - percentages[i]) * PERCENTAGE_CONSTANT_DOWN;
                }
            }

        } else {
            percentageBonus = 0.0;

            for (int i = 0, size = formula.getNumberOfClauses(); i < size; i++) {
                if (formula.getClause(i).isSatisfied(assignment)) {
                    percentageBonus += PERCENTAGE_UNIT_AMOUNT * (1 - percentages[i]);

                } else {
                    percentageBonus -= PERCENTAGE_UNIT_AMOUNT * (1 - percentages[i]);
                }
            }
        }
    }

    /**
     * Returns the number of satisfied clauses.
     *
     * @return the number of satisfied clauses
     */
    public int getNumberOfSatisfied() {
        return numberOfSatisfied;
    }

    /**
     * Returns {@code true} if the formula is satisfied by the set assignment.
     *
     * @return {@code true} if the formula is satisfied by the set assignment
     */
    public boolean isSatisfied() {
        return formula.isSatisfied(assignment);
    }

    /**
     * Returns the percentage bonus.
     *
     * @return the percentage bonus
     */
    public double getPercentageBonus() {
        return percentageBonus;
    }

    /**
     * Returns the percentage for a given index.
     *
     * @param index the index whose percentage should be returned
     * @return the percentage for a given index
     */
    public double getPercentage(int index) {
        return percentages[index];
    }
}

