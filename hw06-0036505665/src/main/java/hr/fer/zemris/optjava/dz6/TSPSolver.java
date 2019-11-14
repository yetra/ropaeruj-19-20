package hr.fer.zemris.optjava.dz6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A program for solving the specified TSP using the Max-Min Ant System algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class TSPSolver {

    /**
     * The evaporation constant.
     */
    private static final double RHO = 0.02;

    /**
     * The alpha constant.
     */
    private static final double ALPHA = 0.995;

    /**
     * The beta constant.
     */
    private static final double BETA = 3.5;

    /**
     * The probability of reconstructing the optimal TSP tour.
     */
    private static final double P = 0.9;

    /**
     * The main method. Reads TSP data and runs the MMAS algorithm.
     *
     * @param args the command-line arguments, 4 expected - a path to the TSP data file,
     *             the number of closest city neighbors to find, the size of the ant population,
     *             the maximum number of iterations before the algorithm terminates
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Expected 4 arguments, got " + args.length);
            System.exit(1);
        }

        int closestCount = Integer.parseInt(args[1]);
        int antsCount = Integer.parseInt(args[2]);
        int maxIterations = Integer.parseInt(args[3]);

        List<City> cities = null;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            cities = parseCities(br);

        } catch (IOException e) {
            System.out.println("An I/O exception occured!");
            System.exit(1);
        }

        new TSPMMAS(cities, closestCount, antsCount, maxIterations,
                RHO, ALPHA, BETA, getA(cities.size())).run();
    }

    /**
     * Parses a TSP data file using the given {@link BufferedReader}.
     *
     * @param br the {@link BufferedReader} to use
     * @return a list of parsed cities
     * @throws IOException if an I/O error occurs
     */
    private static List<City> parseCities(BufferedReader br) throws IOException {
        List<City> cities = new ArrayList<>();

        String line;
        while (!(line = br.readLine().trim()).startsWith("EOF")) {
            if (!Character.isDigit(line.charAt(0))) {
                continue;
            }

            String[] parts = line.split("\\s+");
            int index = Integer.parseInt(parts[0]) - 1;
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);

            cities.add(new City(index, x, y));
        }

        return cities;
    }

    /**
     * Calculates the a parameter.
     *
     * @param cityCount the number of TSP cities
     * @return the a parameter
     */
    private static double getA(int cityCount) {
        double mu = (cityCount - 1) / ((Math.pow(P, -1.0 / cityCount) - 1) * cityCount);

        return mu * cityCount;
    }
}
