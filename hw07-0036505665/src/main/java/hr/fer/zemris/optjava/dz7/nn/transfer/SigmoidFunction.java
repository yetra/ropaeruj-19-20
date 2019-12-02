package hr.fer.zemris.optjava.dz7.nn.transfer;

/**
 * A sigmoid neural network transfer function.
 *
 * @author Bruna Dujmović
 * 
 */
public class SigmoidFunction implements TransferFunction {

    @Override
    public double map(double input) {
        return 1 / (1 + Math.exp(-input));
    }
}
