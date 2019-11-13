package hr.fer.zemris.optjava.dz6;

import java.util.List;
import java.util.Random;

public class AntSystem {
    /**
     * An array of cities for solving the TSP.
     */
    private City[] cities;

    /**
     * The random number generator.
     */
    private Random rand;

    /**
     * An array of city indexes (always 0, 1, 2, 3, ...).
     */
    private int[] indexes;

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
     * A helper array of indexes of an ant's reachable cities.
     */
    private int[] reachable;

    /**
     * A helper array of an ant's probabilities for choosing each city.
     */
    private double[] probabilities;

    /**
     * The evaporation constant.
     */
    private double ro;

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
     * Konstruktor.
     *
     * @param cities lista gradova
     */
    public AntSystem(List<City> cities) {
        this.cities = new City[cities.size()];
        cities.toArray(this.cities);

        ro = 0.2;
        alpha = 3;
        beta = 2;
        rand = new Random();

        indexes = new int[this.cities.length];
        ArraysUtil.linearFillArray(indexes);

        probabilities = new double[this.cities.length];
        reachable = new int[this.cities.length];
        distances = new double[this.cities.length][this.cities.length];
        heuristics = new double[this.cities.length][this.cities.length];
        trails = new double[this.cities.length][this.cities.length];

        double tauInitial = tauMax;

        for(int i = 0; i < this.cities.length; i++) {
            City a = this.cities[i];
            distances[i][i] = 0;
            trails[i][i] = tauInitial;

            for(int j = i + 1; j < this.cities.length; j++) {
                City b = this.cities[j];

                double distance = Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
                distances[i][j] = distance;
                distances[j][i] = distance;

                trails[i][j] = tauInitial;
                trails[j][i] = tauInitial;

                heuristics[i][j] = Math.pow(1.0 / distance, beta);
                heuristics[j][i] = heuristics[i][j];
            }
        }

        int m = 30;
        ants = new TSPSolution[m];
        for(int i = 0; i < ants.length; i++) {
            ants[i] = new TSPSolution();
            ants[i].cityIndexes = new int[this.cities.length];
        }

        best = new TSPSolution();
        best.cityIndexes = new int[this.cities.length];
    }

    /**
     * Glavna metoda algoritma.
     */
    public void go() {
        int maxIterations = 500;

        int iteration = 0;
        while(iteration < maxIterations) {
            for (TSPSolution ant : ants) {
                doWalk(ant);
            }

            updateTrails(best);
            evaporateTrails();
            checkBestSolution();

            iteration++;
        }

        System.out.println("Best length: "+best.tourLength);
        System.out.println(best);
    }

    /**
     * Metoda koja obavlja hod jednog mrava.
     *
     * @param ant mrav
     */
    private void doWalk(TSPSolution ant) {
        // Svi su gradovi dostupni
        System.arraycopy(indexes, 0, reachable, 0, indexes.length);
        // Permutirajmo redosljed gradova tako da krenemo iz slučajnog
        ArraysUtil.shuffleArray(reachable, rand);

        // Neka je prvi grad fiksiran
        ant.cityIndexes[0] = reachable[0];

        // reb t amo utvrditi kamo iz drugoga pa na dalje:
        for (int step = 1; step < cities.length - 1; step++) {
            int previousCityIndex = ant.cityIndexes[step - 1];

            // Koji grad biram u koraku "step"?
            // Mogu ici u sve gradove od step do cities.length-1

            double probSum = 0.0;
            for (int candidate = step; candidate < cities.length; candidate++) {
                int cityIndex = reachable[candidate];

                probabilities[cityIndex] = Math.pow(trails[previousCityIndex][cityIndex],alpha) *
                        heuristics[previousCityIndex][cityIndex];

                probSum += probabilities[cityIndex];
            }

            // Normalizacija vjerojatnosti:
            for(int candidate = step; candidate < cities.length; candidate++) {
                int cityIndex = reachable[candidate];
                probabilities[cityIndex] = probabilities[cityIndex] / probSum;
            }

            // Odluka kuda dalje?
            double number = rand.nextDouble();

            probSum = 0.0;
            int selectedCandidate = -1;
            for (int candidate = step; candidate < cities.length; candidate++) {
                int cityIndex = reachable[candidate];
                probSum += probabilities[cityIndex];

                if (number <= probSum) {
                    selectedCandidate = candidate;
                    break;
                }
            }

            if (selectedCandidate == -1) {
                selectedCandidate = cities.length - 1;
            }

            int tmp = reachable[step];
            reachable[step] = reachable[selectedCandidate];
            reachable[selectedCandidate] = tmp;
            ant.cityIndexes[step] = reachable[step];
        }

        ant.cityIndexes[ant.cityIndexes.length-1] = reachable[ant.cityIndexes.length-1];
        TSPUtil.evaluate(ant, distances);
    }
    /**
     * Metoda koja obavlja ažuriranje feromonskih tragova
     */
    private void updateTrails(TSPSolution ant) {
        double delta = 1.0 / ant.tourLength;

        for(int i = 0; i < ant.cityIndexes.length - 1; i++) {
            int a = ant.cityIndexes[i];
            int b = ant.cityIndexes[i + 1];

            trails[a][b] = Math.min(trails[a][b] + delta, tauMax);
            trails[b][a] = trails[a][b];
        }
    }

    /**
     * Metoda koja obavlja isparavanje feromonskih tragova.
     */
    private void evaporateTrails() {
        for(int i = 0; i < this.cities.length; i++) {
            for(int j = i + 1; j < this.cities.length; j++) {
                trails[i][j] = Math.max(trails[i][j] * (1 - ro), tauMin);
                trails[j][i] = trails[i][j];
            }
        }
    }

    /**
     * Metoda provjerava je li pronađeno bolje rješenje od
     * prethodno najboljeg.
     */
    private void checkBestSolution() {
        // Nadi najbolju rutu
        if (!haveBest) {
            haveBest = true;
            TSPSolution ant = ants[0];
            System.arraycopy(ant.cityIndexes, 0, best.cityIndexes, 0, ant.cityIndexes.length);
            best.tourLength = ant.tourLength;
        }

        double currentBest = best.tourLength;
        int bestIndex = -1;
        for (int antIndex = 0; antIndex < ants.length; antIndex++) {
            TSPSolution ant = ants[antIndex];

            if (ant.tourLength < currentBest) {
                currentBest = ant.tourLength;
                bestIndex = antIndex;
            }
        }

        if (bestIndex != -1) {
            TSPSolution ant = ants[bestIndex];
            System.arraycopy(ant.cityIndexes, 0, best.cityIndexes, 0, ant.cityIndexes.length);
            best.tourLength = ant.tourLength;
        }
    }
}
