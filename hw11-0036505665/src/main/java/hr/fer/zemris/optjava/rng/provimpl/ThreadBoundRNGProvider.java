package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * An implementation of {@link IRNGProvider} which assumes that
 * the current thread also implements {@link IRNGProvider}.
 *
 * @author Bruna Dujmović
 *
 */
public class ThreadBoundRNGProvider implements IRNGProvider {

    @Override
    public IRNG getRNG() {
        return ((IRNGProvider) Thread.currentThread()).getRNG();
    }
}
