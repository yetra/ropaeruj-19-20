package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.List;

public class TSPSolution {

    public int tourLength;

    public List<City> route;

    public TSPSolution(int cityCount) {
        route = new ArrayList<>(cityCount);
    }
}
