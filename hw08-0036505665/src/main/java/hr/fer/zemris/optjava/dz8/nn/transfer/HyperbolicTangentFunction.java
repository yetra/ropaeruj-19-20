package hr.fer.zemris.optjava.dz8.nn.transfer;

/**
 * A hyperbolic tangent neural network transfer function.
 *
 * @author Bruna Dujmović
 *
 */
public class HyperbolicTangentFunction implements TransferFunction {

    @Override
    public double map(double input) {
        double negativeInputExp = Math.exp(-input);

        return (1 - negativeInputExp) / (1 + negativeInputExp);
    }
}
