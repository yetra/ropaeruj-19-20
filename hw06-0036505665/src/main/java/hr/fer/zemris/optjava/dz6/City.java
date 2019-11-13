package hr.fer.zemris.optjava.dz6;

public class City {
    public int index;
    public int x;
    public int y;

    public City(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public double distanceTo(City other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }
}
