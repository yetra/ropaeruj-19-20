package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AntSystem {

    /**
     * The number of cities in this TSP.
     */
    private final int cityCount;

    /**
     * A list of cities for solving the TSP.
     */
    private List<City> cities;

    /**
     * A symmetric matrix of pheromone trails.
     */
    private double[][] trails;

    /**
     * A symmetric matrix of distances between cities.
     */
    private double[][] distances;

    /**
     * The heuristics matrix.
     */
    private double[][] heuristics;

    /**
     * The ant population for solving the TSP.
     */
    private TSPSolution[] ants;

    /**
     * A helper array of an ant's probabilities for choosing each city.
     */
    private double[] probabilities;

    /**
     * The evaporation constant.
     */
    private double rho;

    /**
     * The alpha constant.
     */
    private double alpha;

    /**
     * The beta constant.
     */
    private double beta;

    /**
     * The best so far ant.
     */
    private TSPSolution best;

    /**
     * {@code} true if a best ant exists.
     */
    private boolean haveBest = false;

    /**
     * The lower bound for pheromone values.
     */
    private double tauMin;

    /**
     * The upper bound for pheromone values.
     */
    private double tauMax;

    /**
     * The maximum number of iterations before tha algorithm terminates.
     */
    private final int maxIterations;

    /**
     * Constructs an {@link AntSystem} of the given parameters.
     *
     * @param cities a list of TSP cities
     * @param closestCount the number of closest city neighbors to find
     * @param antsCount the size of the ant population
     * @param maxIterations he maximum number of iterations before tha algorithm terminates
     * @param rho the evaporation constant
     * @param alpha the alpha constant
     * @param beta the beta constant
     */
    public AntSystem(List<City> cities, int closestCount, int antsCount, int maxIterations,
                     double rho, double alpha, double beta) {
        this.cities = cities;
        this.cityCount = cities.size();

        this.rho = rho;
        this.alpha = alpha;
        this.beta = beta;

        this.maxIterations = maxIterations;

        probabilities = new double[cityCount];

        initializeMatrices();
        cities.forEach(city -> city.findClosest(closestCount, cities, distances[city.index]));

        ants = new TSPSolution[antsCount];
        for(int i = 0; i < antsCount; i++) {
            ants[i] = new TSPSolution(cityCount);
        }

        best = new TSPSolution(cityCount);
    }

    /**
     * Initializes the {@link #distances}, {@link #heuristics} and {@link #trails} matrices.
     */
    private void initializeMatrices() {
        distances = new double[cityCount][cityCount];
        heuristics = new double[cityCount][cityCount];
        trails = new double[cityCount][cityCount];

        for(int i = 0; i < cityCount; i++) {
            distances[i][i] = 0;
            trails[i][i] = tauMax;

            for(int j = i + 1; j < cityCount; j++) {
                double distance = cities.get(i).distanceTo(cities.get(j));
                distances[i][j] = distance;
                distances[j][i] = distance;

                trails[i][j] = tauMax;
                trails[j][i] = tauMax;

                heuristics[i][j] = Math.pow(1.0 / distance, beta);
                heuristics[j][i] = heuristics[i][j];
            }
        }
    }

    /**
     * Glavna metoda algoritma.
     */
    public void go() {
        int iteration = 0;
        while(iteration < maxIterations) {
            TSPSolution iterationBest = null;

            for (TSPSolution ant : ants) {
                doWalk(ant);

                if (iterationBest == null || ant.tourLength < iterationBest.tourLength) {
                    iterationBest = ant;
                }
            }

            assert iterationBest != null;
            if (best == null || iterationBest.tourLength < best.tourLength) {
                best = iterationBest;
            }

            updateTrails(best);
            evaporateTrails();

            iteration++;
        }

        System.out.println("Best length: " + best.tourLength);
        System.out.println(best);
    }

    /**
     * Metoda koja obavlja hod jednog mrava.
     *
     * @param ant mrav
     */
    private void doWalk(TSPSolution ant) {
        City currentCity = ant.getInitialCity();

        for (int i = 0; i < cityCount - 1; i++) {
            City nextCity;

            if (!ant.visitedAll(currentCity.closestCities)) {
                nextCity = pickNextCity(currentCity, currentCity.closestCities, ant);
            } else {
                nextCity = pickNextCity(currentCity, cities, ant);
            }

            ant.visit(nextCity);
            currentCity = nextCity;
        }

        evaluate(ant);
    }

    /**
     * Picks an ant's next city from a given list of cities.
     *
     * @param currentCity the current city
     * @param cities the list of cities
     * @param ant the ant whose next city should be found
     * @return the ant's next city
     */
    private City pickNextCity(City currentCity, List<City> cities, TSPSolution ant) {
        City nextCity = null;
        double probabilitiesSum = 0;

        for (City city : cities) {
            probabilities[city.index] = Math.pow(trails[currentCity.index][city.index], alpha)
                    * heuristics[currentCity.index][city.index];

            probabilitiesSum += probabilities[city.index];
        }

        double sum = 0;
        double number = ThreadLocalRandom.current().nextDouble();
        for (City city : cities) {
            sum += probabilities[city.index] / probabilitiesSum;

            if (number <= sum && !ant.visited(city)) {
                nextCity = city;
                break;
            }
        }

        if (nextCity == null) {
            for (City city : cities) {
                if (!ant.visited(city)) {
                    nextCity = city;
                    break;
                }
            }
        }

        return nextCity;
    }

    /**
     * Evaluates the given ant.
     *
     * @param ant the ant to evaluate.
     */
    private void evaluate(TSPSolution ant) {
        ant.tourLength = 0;

        for (int i = 0; i < cityCount - 1; i++) {
            ant.tourLength += distances[ant.tour.get(i).index][ant.tour.get(i + 1).index];
        }
        ant.tourLength += distances[ant.tour.get(0).index][ant.tour.get(cityCount - 1).index];
    }

    /**
     * Metoda koja obavlja ažuriranje feromonskih tragova
     */
    private void updateTrails(TSPSolution ant) {
        double delta = 1.0 / ant.tourLength;

        for(int i = 0; i < cityCount - 1; i++) {
            int a = ant.tour.get(i).index;
            int b = ant.tour.get(i + 1).index;

            trails[a][b] = Math.min(trails[a][b] + delta, tauMax);
            trails[b][a] = trails[a][b];
        }
    }

    /**
     * Metoda koja obavlja isparavanje feromonskih tragova.
     */
    private void evaporateTrails() {
        for(int i = 0; i < cityCount; i++) {
            for(int j = i + 1; j < cityCount; j++) {
                trails[i][j] = Math.max(trails[i][j] * (1 - rho), tauMin);
                trails[j][i] = trails[i][j];
            }
        }
    }
}
