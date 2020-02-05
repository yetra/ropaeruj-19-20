package hr.fer.zemris.optjava.dz13.ant;

import java.util.ArrayList;
import java.util.List;

/**
 * Models an ant for solving the Santa Fe Trail problem.
 *
 * @author Bruna Dujmović
 *
 */
public class Ant {

    /**
     * The maximum energy value of this ant.
     */
    private static final int MAX_ENERGY = 600;

    /**
     * The current position of this ant.
     */
    private Position position;

    /**
     * The current direction (orientation) of this ant.
     */
    private Direction direction;

    /**
     * The current energy value of this ant.
     */
    private int energy;

    /**
     * The number of food pieces eaten by this ant.
     */
    private int foodEaten;

    /**
     * The ant trail matrix.
     */
    private boolean[][] trail;

    /**
     * A list of all positions that this ant has been in.
     */
    private List<Position> positions;

    /**
     * Constructs an {@link Ant} that can be controlled on the given trail.
     *
     * @param trail the ant trail matrix
     */
    public Ant(boolean[][] trail) {
        this.trail = trail;
        this.position = new Position(0, 0);
        this.direction = Direction.EAST;
        this.energy = MAX_ENERGY;
        this.foodEaten = 0;

        this.positions = new ArrayList<>();
        this.positions.add(position.copy());
    }

    /**
     * Returns the current energy value of this ant.
     *
     * @return the current energy value of this ant
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Returns the number of food pieces eaten by this ant.
     *
     * @return the number of food pieces eaten by this ant
     */
    public int getFoodEaten() {
        return foodEaten;
    }

    /**
     * Returns a list of all positions that this ant has been in.
     *
     * @return a list of all positions that this ant has been in
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Moves this ant to the cell directly in front of it.
     */
    public void move() {
        if (energy == 0) {
            return;
        }

        position.next(trail, direction);
        positions.add(position.copy());

        if (trail[position.y][position.x]) {
            trail[position.y][position.x] = false;
            foodEaten++;
        }

        energy--;
    }

    /**
     * Turns this ant to the left by 90 degrees.
     */
    public void turnLeft() {
        if (energy == 0) {
            return;
        }

        switch (direction) {
            case EAST:
                direction = Direction.NORTH;
                break;

            case WEST:
                direction = Direction.SOUTH;
                break;

            case NORTH:
                direction = Direction.WEST;
                break;

            case SOUTH:
                direction = Direction.EAST;
                break;
        }

        energy--;
    }

    /**
     * Turns this ant to the right by 90 degrees.
     */
    public void turnRight() {
        if (energy == 0) {
            return;
        }

        switch (direction) {
            case EAST:
                direction = Direction.SOUTH;
                break;

            case WEST:
                direction = Direction.NORTH;
                break;

            case NORTH:
                direction = Direction.EAST;
                break;

            case SOUTH:
                direction = Direction.WEST;
                break;
        }

        energy--;
    }

    /**
     * Returns {@code true} if a food piece is on the cell directly in front of this ant.
     *
     * @return {@code true} if a food piece is on the cell directly in front of this ant
     */
    public boolean isFoodAhead() {
        Position ahead = position.getNext(trail, direction);

        return trail[ahead.y][ahead.x];
    }
}
