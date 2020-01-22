package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
     * The random number generator to use.
     */
    private IRNG rng = RNG.getRNG();

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
            int randomIndex = rng.nextInt(0, population.size());
            tournament.add(populationList.get(randomIndex));
        }

        return Collections.min(tournament);
    }
}
