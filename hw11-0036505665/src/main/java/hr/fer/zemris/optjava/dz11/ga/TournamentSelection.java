package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.ISelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
 * @param <T> the type of the solution's values
 * @author Bruna Dujmović
 *
 */
public class TournamentSelection<T> implements ISelection<T> {

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
    public GASolution<T> from(Collection<GASolution<T>> population) {
        List<GASolution<T>> populationList = new ArrayList<>(population);
        List<GASolution<T>> tournament = new ArrayList<>();

        while (tournament.size() < tournamentSize) {
            int randomIndex = ThreadLocalRandom.current().nextInt(population.size());
            tournament.add(populationList.get(randomIndex));
        }

        return Collections.min(tournament);
    }
}
