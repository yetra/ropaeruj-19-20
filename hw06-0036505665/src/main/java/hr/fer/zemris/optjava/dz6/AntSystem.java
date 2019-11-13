package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AntSystem {
    // Polje gradova
    private City[] cities;

    // Generator slučajnih brojeva
    private Random rand;

    // Polj in e deksa radova (uvijek oblika 0, 1, 2, 3, ...).
    private int[] indexes;

    // Fero onski m tragovi - simetrična matrica
    private double[][] trails;

    // Udaljenosti između gradova - simetrična matrica
    private double[][] distances;

    // Heurističke vrijednosti
    private double[][] heuristics;

    // Populacija mrava koji rješavaju problem
    private TSPSolution[] ants;

    // Pomo no ć polje indeksa mravu dostupnih gradova
    private int[] reachable;

    // Pomoćno polje vjerojatnosti odabira grada
    private double[] probabilities;
    // Konstanta isparavanja
    private double ro;

    // Kons anta a t lfa
    private double alpha;
    // Kons anta b t eta
    private double beta;
    // Pomoćno rješenje koje pamti najbolju pronađenu turu - ikada.
    private TSPSolution best;
    private boolean haveBest = false;

    private double tauMin;
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
        rand = new Random();

        indexes = new int[this.cities.length];
        ArraysUtil.linearFillArray(indexes);

        probabilities = new double[this.cities.length];
        reachable = new int[this.cities.length];
        distances = new double[this.cities.length][this.cities.length];
        heuristics = new double[this.cities.length][this.cities.length];
        trails = new double[this.cities.length][this.cities.length];

        double initTrail = 1.0/5000.0;
        int m = 30;
        alpha = 3;
        beta = 2;

        for(int i = 0; i < this.cities.length; i++) {
            City a = this.cities[i];
            distances[i][i] = 0;
            trails[i][i] = initTrail;

            for(int j = i+1; j < this.cities.length; j++) {
                City b = this.cities[j];
                double dist = Math.sqrt((a.x-b.x)*(a.x-b.x)+(a.y-b.y)*(a.y-b.y));
                distances[i][j] = dist;
                distances[j][i] = dist;
                trails[i][j] = initTrail;
                trails[j][i] = initTrail;
                heuristics[i][j] = Math.pow(1.0 / dist, beta);
                heuristics[j][i] = heuristics[i][j];
            }
        }
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
        System.out.println("Zapocinjem s populacijom:");
        System.out.println("=========================");
        int iter = 0;
        int iterLimit = 500;

        // ponavljaj dozvoljeni broj puta
        while(iter < iterLimit) {
            iter++;

            for (int antIndex = 0; antIndex < ants.length; antIndex++) {
                // S kojim mravom radim?
                TSPSolution ant = ants[antIndex];
                doWalk(ant);
            }

            updateTrails();
            evaporateTrails();
            checkBestSolution();
        }

        System.out.println("Best length: "+best.tourLength);
        System.out.println(best);

        PrepareTSP.visualize(TSPUtil.reorderCities(cities, best.cityIndexes));
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
    private void updateTrails() {
        // Koliko mrava radi ažuriranje?
        int updates = ants.length;

        // Ako z elim samo da najbolji rade ažuriranje...
        if(false) {
            updates = 5;
            //ili updates = ants.length / 10;
            TSPUtil.partialSort(ants, updates);
        }

        // Azuriranje feromonskog traga:
        for(int antIndex = 0; antIndex < updates; antIndex++) {
            // S kojim mravom radim?
            TSPSolution ant = ants[antIndex];
            double delta = 1.0 / ant.tourLength;
            for(int i = 0; i < ant.cityIndexes.length-1; i++) {
                int a = ant.cityIndexes[i];
                int b = ant.cityIndexes[i+1];
                trails[a][b] += delta;
                trails[b][a] = trails[a][b];
            }
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
