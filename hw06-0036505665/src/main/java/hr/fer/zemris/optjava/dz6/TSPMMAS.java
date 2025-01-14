package hr.fer.zemris.optjava.dz6;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of Max-Min Ant System for solving the Travelling Salesman Problem (TSP).
 *
 * @author Bruna Dujmović
 *
 */
public class TSPMMAS {

    /**
     * The maximum allowed number of iterations where the best solution stagnates.
     */
    private static final int MAX_STAGNATION_COUNT = 100;

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
    private TSPAnt[] ants;

    /**
     * A helper array of an ant's probabilities for choosing each city.
     */
    private double[] probabilities;

    /**
     * The evaporation constant.
     */
    private final double rho;

    /**
     * The alpha constant.
     */
    private final double alpha;

    /**
     * The beta constant.
     */
    private final double beta;

    /**
     * The a parameter for calculating {@link #tauMin}.
     */
    private final double a;

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
     * Constructs a {@link TSPMMAS} of the given parameters.
     *
     * @param cities a list of TSP cities
     * @param closestCount the number of closest city neighbors to find
     * @param antsCount the size of the ant population
     * @param maxIterations he maximum number of iterations before tha algorithm terminates
     * @param rho the evaporation constant
     * @param alpha the alpha constant
     * @param beta the beta constant
     * @param a the a parameter for calculating {@link #tauMin}
     */
    public TSPMMAS(List<City> cities, int closestCount, int antsCount, int maxIterations,
                   double rho, double alpha, double beta, double a) {
        this.cities = cities;
        this.cityCount = cities.size();

        this.maxIterations = maxIterations;
        this.rho = rho;
        this.alpha = alpha;
        this.beta = beta;
        this.a = a;

        probabilities = new double[cityCount];

        initializeMatrices();
        initializeAnts(antsCount);
        cities.forEach(city -> city.findClosest(closestCount, cities, distances[city.index]));
        
        this.tauMax = getTauInitial();
        this.tauMin = tauMax / a;
        initializeAnts(antsCount);
        initializeTrails();
    }

    /**
     * Initializes the {@link #distances}, {@link #heuristics} and {@link #trails} matrices.
     */
    private void initializeMatrices() {
        distances = new double[cityCount][cityCount];
        heuristics = new double[cityCount][cityCount];

        for(int i = 0; i < cityCount; i++) {
            distances[i][i] = 0;

            for(int j = i + 1; j < cityCount; j++) {
                double distance = cities.get(i).distanceTo(cities.get(j));
                distances[i][j] = distance;
                distances[j][i] = distance;

                heuristics[i][j] = Math.pow(1.0 / distance, beta);
                heuristics[j][i] = heuristics[i][j];
            }
        }
    }

    /**
     * Initializes the matrix of pheromone trails.
     */
    private void initializeTrails() {
        trails = new double[cityCount][cityCount];

        for(int i = 0; i < cityCount; i++) {
            trails[i][i] = tauMax;

            for(int j = i + 1; j < cityCount; j++) {
                trails[i][j] = tauMax;
                trails[j][i] = tauMax;
            }
        }
    }

    /**
     * Initializes the ant population for solving the TSP.
     *
     * @param antsCount the size of the ant population
     */
    private void initializeAnts(int antsCount) {
        if (ants == null) {
            ants = new TSPAnt[antsCount];
        }

        for(int i = 0; i < antsCount; i++) {
            int initialCityIndex = ThreadLocalRandom.current().nextInt(cityCount);

            ants[i] = new TSPAnt(cityCount);
            ants[i].visit(cities.get(initialCityIndex));
        }
    }

    /**
     * Calculates the initial {@link #tauMax}.
     *
     * @return the initial {@link #tauMax}
     */
    private double getTauInitial() {
        TSPAnt best = null;

        for (TSPAnt ant : ants) {
            City currentCity = ant.getInitialCity();

            for (int j = 0; j < cityCount - 1; j++) {
                City nextCity = currentCity.closestCities.get(0);

                ant.visit(nextCity);
                currentCity = nextCity;
            }

            evaluate(ant);

            if (best == null || ant.tourLength < best.tourLength) {
                best = ant;
            }
        }

        assert best != null;
        return 1.0 / (rho * best.tourLength);
    }

    /**
     * Executes the algorithm.
     */
    public void run() {
        TSPAnt bestSoFar = null;
        int stagnationCount = 0;

        int iteration = 0;
        while(iteration < maxIterations) {
            TSPAnt iterationBest = null;

            for (TSPAnt ant : ants) {
                walk(ant);

                if (iterationBest == null || ant.tourLength < iterationBest.tourLength) {
                    iterationBest = ant;
                }
            }

            assert iterationBest != null;
            if (bestSoFar == null || iterationBest.tourLength < bestSoFar.tourLength) {
                bestSoFar = iterationBest;
                stagnationCount = 0;
            } else {
                stagnationCount++;
            }

            evaporateTrails();

            if (stagnationCount >= MAX_STAGNATION_COUNT) {
                tauMax = 1.0 / (rho * bestSoFar.tourLength);
                tauMin = tauMax / a;
                initializeTrails();
                stagnationCount = 0;
            } else {
                updateTrails((iteration < maxIterations / 2) ? iterationBest : bestSoFar);
            }

            initializeAnts(ants.length);
            iteration++;

            System.out.println("Best tour length so far: " + bestSoFar.tourLength);
        }

        System.out.println(bestSoFar);
    }

    /**
     * Makes the given ant visit all cities.
     *
     * @param ant the and to walk
     */
    private void walk(TSPAnt ant) {
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
    private City pickNextCity(City currentCity, List<City> cities, TSPAnt ant) {
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
    private void evaluate(TSPAnt ant) {
        ant.tourLength = 0;

        for (int i = 0; i < cityCount - 1; i++) {
            ant.tourLength += distances[ant.tour.get(i).index][ant.tour.get(i + 1).index];
        }
        ant.tourLength += distances[ant.tour.get(0).index][ant.tour.get(cityCount - 1).index];
    }

    /**
     * Updates the pheromone trails using the given ant.
     *
     * @param ant the ant for updating the pheromone trails
     */
    private void updateTrails(TSPAnt ant) {
        double delta = 1.0 / ant.tourLength;

        for(int i = 0; i < cityCount - 1; i++) {
            int a = ant.tour.get(i).index;
            int b = ant.tour.get(i + 1).index;

            trails[a][b] = Math.min(trails[a][b] + delta, tauMax);
            trails[b][a] = trails[a][b];
        }
    }

    /**
     * Evaporates the pheromone trails.
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
