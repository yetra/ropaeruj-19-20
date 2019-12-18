package hr.fer.zemris.optjava.dz8.ann.transfer;

/**
 * An interface to be implemented by various kinds of neural network transfer functions.
 *
 * @author Bruna Dujmović
 *
 */
public interface TransferFunction {

    /**
     * Maps the given input to an output.
     *
     * @param input the input value to map to an output
     * @return the output obtained by mapping the input
     */
    double map(double input);
}
