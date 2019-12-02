package hr.fer.zemris.optjava.dz7;

/**
 * An interface representing a read-only dataset.
 */
public interface ReadOnlyDataset {
    /*
     TODO Razred čiji se ovdje predaje objekt trebao bi ponuditi metode poput:
     koliko ima uzoraka za učenje, koliko uzorak ima ulaza, koliko ima izlaza,
     dohvat i-tog uzorka (moguće čak i parcijalno: posebno ulaznog dijela, posebno izlaznog dijela) i slično.
     */

    /**
     * Returns the number of samples in this dataset.
     *
     * @return the number of samples in this dataset
     */
    int getNumberOfSamples();
}
