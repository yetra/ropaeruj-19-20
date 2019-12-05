package hr.fer.zemris.optjava.dz7;

/**
 * An interface representing a read-only dataset.
 *
 * @author Bruna Dujmović
 * 
 */
public interface ReadOnlyDataset {

    /**
     * Returns the number of samples in this dataset.
     *
     * @return the number of samples in this dataset
     */
    int getSamplesCount();

    /**
     * Returns the number of inputs in each sample in this dataset.
     *
     * @return the number of inputs in each sample in this dataset
     */
    int getInputsCount();

    /**
     * Returns the number of outputs in each sample in this dataset.
     *
     * @return the number of outputs in each sample in this dataset
     */
    int getOutputsCount();

    /**
     * Returns the index-th input in this dataset.
     *
     * @param index the index of the input to get
     * @return the index-th input in this dataset
     */
    double[] getInput(int index);

    /**
     * Returns the index-th output in this dataset.
     *
     * @param index the index of the output to get
     * @return the index-th output in this dataset
     */
    double[] getOutput(int index);
}
