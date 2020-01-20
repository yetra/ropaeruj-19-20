package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * An implementation of {@link IRNGProvider} that uses {@link ThreadLocal}.
 * Each thread has its own instance of {@link IRNG} that it can access via {@link #getRNG()}.
 *
 * @author Bruna Dujmović
 *
 */
public class ThreadLocalRNGProvider implements IRNGProvider {

    /**
     * Thread-local {@link IRNG} instances each belonging to a specific thread.
     */
    private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>();

    @Override
    public IRNG getRNG() {
        IRNG rng = threadLocal.get();

        if (rng == null) {
            rng = new RNGRandomImpl();
            threadLocal.set(rng);
        }

        return rng;
    }
}
