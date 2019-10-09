package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm1 {

    private SATFormula formula;

    private BitVector solution;

    public Algorithm1(SATFormula formula) {
        this.formula = formula;
    }

    public BitVector execute() {
        double maximumValue = Math.pow(2, formula.getNumberOfClauses());
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
