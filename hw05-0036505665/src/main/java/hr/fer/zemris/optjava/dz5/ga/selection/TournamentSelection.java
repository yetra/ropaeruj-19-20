package hr.fer.zemris.optjava.dz5.ga.selection;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of tournament selection.
 *
 * The size of the tournament is specified using the {@link #tournamentSize} field.
 * For example, if {@link #tournamentSize} is 4 then 4 chromosomes will be chosen from
 * the population to form a tournament, and the best one among them will be selected.
 *
 * This implementation makes sure that all chromosomes in a tournament are unique.
 *
 * @author Bruna Dujmović
 *
 */
public class TournamentSelection implements ISelection {

    /**
     * The minimum tournament size.
     */
    private static final int MIN_TOURNAMENT_SIZE = 2;

    /**
     * The size of the tournament.
     */
    private int tournamentSize;

    /**
     * Constructs a {@link TournamentSelection} of a given size.
     *
     * @param tournamentSize the size of the tournament
     * @throws IllegalArgumentException if the size is less than the minimum size
     */
    public TournamentSelection(int tournamentSize) {
        if (tournamentSize < MIN_TOURNAMENT_SIZE) {
            throw new IllegalArgumentException("Tournament size must be >= " + MIN_TOURNAMENT_SIZE);
        }

        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome from(List<Chromosome> population) {
        List<Chromosome> tournament = new ArrayList<>();

        while (tournament.size() < tournamentSize) {
            int randomIndex = ThreadLocalRandom.current().nextInt(population.size());
            tournament.add(population.get(randomIndex));
        }

        return Collections.min(tournament);
    }
}
