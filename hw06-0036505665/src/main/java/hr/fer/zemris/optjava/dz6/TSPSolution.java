package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSPSolution {

    public int tourLength;

    public List<City> route;

    public Set<City> visited;

    public TSPSolution(int cityCount) {
        route = new ArrayList<>(cityCount);
        visited = new HashSet<>();
    }
}
