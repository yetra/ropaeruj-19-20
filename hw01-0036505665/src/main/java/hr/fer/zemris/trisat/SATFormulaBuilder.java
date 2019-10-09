package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A helper class for creating {@link SATFormula} objects.
 *
 * @author Bruna Dujmović
 *
 */
public class SATFormulaBuilder {

    /**
     * The number of variables used in the formula.
     */
    private static int numberOfVariables;

    /**
     * The number of clauses in the formula.
     */
    private static int numberOfClauses;

    /**
     * A counter of formula clauses.
     */
    private static int clauseCount;

    /**
     * An array of formula clauses.
     */
    private static Clause[] clauses;

    /**
     * Returns a {@link SATFormula} object constructed from a given file.
     *
     * @param filePath a path to the file containing {@link SATFormula} data
     * @return a {@link SATFormula} object constructed from a given file
     * @throws IOException if an I/O error occurs when opening the file
     * @throws IllegalArgumentException if the file is incorrectly formatted
     */
    public static SATFormula fromFile(Path filePath) throws IOException {
        BufferedReader br = Files.newBufferedReader(filePath);

        String line;
        while (!((line = br.readLine()).startsWith("%"))) {

            if (line.startsWith("c")) {
                continue;
            }
            String[] parts = line.split("\\s+");

            if (parts[0].startsWith("p") && numberOfVariables == 0) {
                numberOfVariables = Integer.parseInt(parts[2]);
                numberOfClauses = Integer.parseInt(parts[3]);
                clauses = new Clause[numberOfClauses];

            } else {
                if (parts.length != numberOfVariables + 1) {
                    throw new IllegalArgumentException("Invalid number of variables!");
                }

                int[] indexes = new int[numberOfVariables];
                for (int i = 0; i < numberOfVariables; i++) {
                    indexes[i] = Integer.parseInt(parts[i]);
                }

                clauses[clauseCount++] = new Clause(indexes);
            }
        }

        br.close();

        if (numberOfClauses != clauseCount) {
            throw new IllegalArgumentException("Invalid number of clauses!");
        }

        return new SATFormula(numberOfVariables, clauses);
    }
}
