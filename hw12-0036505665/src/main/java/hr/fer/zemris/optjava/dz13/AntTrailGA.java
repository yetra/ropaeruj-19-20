package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.Trail;
import hr.fer.zemris.optjava.dz13.gp.GP;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main program. Runs the GP algorithm, writes the best result to the specified file and displays it.
 *
 * Usage examples:
 * 13-SantaFeAntTrail.txt 100 500 89 best.txt
 *
 * @author Bruna Dujmović
 *
 */
public class AntTrailGA {

    /**
     * The main method. Runs the GP algorithm, writes the best result to the specified file and displays it.
     *
     * @param args the command-line arguments, 5 expected - the path to the ant trail definition file,
     *             the maximum number of algorithm iterations, the size of the population,
     *             the minimum fitness value which, if reached, will terminate the algorithm,
     *             the path to the file that will contain the best result
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.out.println("Expected 5 arguments, got " + args.length);
            System.exit(1);
        }

        Trail trail = Trail.fromFile(Paths.get(args[0]));
        int maxIterations = Integer.parseInt(args[1]);
        int populationSize = Integer.parseInt(args[2]);
        double minFitness = Double.parseDouble(args[3]);
        Path savePath = Paths.get(args[4]);

        Tree best = new GP(populationSize, maxIterations, minFitness, trail).run();

        Files.writeString(savePath, best.toString());

        Ant ant = new Ant(trail.getMatrix());
        best.calculateFitness(ant, null);

        TrailFrame frame = new TrailFrame(trail.getMatrix(), ant);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
