package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSPSolution {

    public int tourLength;

    public List<City> tour;

    public Set<City> visited;

    public TSPSolution(int cityCount) {
        tour = new ArrayList<>(cityCount);
        visited = new HashSet<>();
    }

    public void visit(City city) {
        tour.add(city);
        visited.add(city);
    }

    public boolean visited(City city) {
        return visited.contains(city);
    }

    public boolean visitedAll(List<City> cities) {
        return visited.containsAll(cities);
    }

    public City getInitialCity() {
        return tour.get(0);
    }
}
