package hr.fer.zemris.optjava.dz13.ant;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Models an ant trail with pieces of food on it.
 *
 * @author Bruna Dujmović
 *
 */
public class Trail {

    /**
     * A matrix representing this trail. If a piece of food is on a certain cell,
     * the corresponding matrix element will be {@code true}.
     */
    private boolean[][] matrix;

    /**
     * Constructs a {@link Trail}.
     *
     * @param matrix a matrix representing the ant trail
     */
    private Trail(boolean[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Returns a copy of the matrix representing this trail. If a piece of food is on a certain cell,
     * the corresponding matrix element will be {@code true}.
     *
     * @return the matrix representing this trail
     */
    public boolean[][] getMatrix() {
        boolean[][] matrixCopy = new boolean[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            matrixCopy[i] = new boolean[matrix[i].length];
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrix[i].length);
        }

        return matrixCopy;
    }

    /**
     * Parses the specified ant trail definition file to a matrix.
     *
     * @param trailPath the path to the ant trail definition file
     */
    public static Trail fromFile(Path trailPath) {
        boolean[][] matrix = new boolean[0][];

        try (BufferedReader br = Files.newBufferedReader(trailPath)) {
            String[] dimensions = br.readLine().split("x");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            int y = 0;

            matrix = new boolean[height][width];

            String line;
            while ((line = br.readLine()) != null) {
                char[] symbols = line.trim().toCharArray();

                for (int x = 0; x < width; x++) {
                    matrix[y][x] = symbols[x] != '0' && symbols[x] != '.';
                }
                y++;
            }

        } catch (IOException e) {
            System.out.println("Can't parse ant trail definition file!");
            System.exit(1);
        }

        return new Trail(matrix);
    }
}
