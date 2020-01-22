package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

/**
 * A {@link Thread} that has its own private random number generator and acts as an {@link IRNGProvider}.
 */
public class EVOThread extends Thread implements IRNGProvider {

    /**
     * The {@link IRNG} implementation to use for generating random numbers.
     */
    private IRNG rng = new RNGRandomImpl();

    /**
     * Constructs an {@link EVOThread}.
     */
    public EVOThread() {
    }

    /**
     * Constructs an {@link EVOThread}.
     */
    public EVOThread(Runnable target) {
        super(target);
    }

    /**
     * Constructs an {@link EVOThread}.
     *
     * @param group the thread group
     * @param target the object whose {@code run} method is invoked when this thread is started
     */
    public EVOThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    /**
     * Constructs an {@link EVOThread}.
     *
     * @param group the thread group
     * @param name the name of the new thread
     */
    public EVOThread(ThreadGroup group, String name) {
        super(group, name);
    }

    /**
     * Constructs an {@link EVOThread}.
     *
     * @param target the object whose {@code run} method is invoked when this thread is started
     * @param name the name of the new thread
     */
    public EVOThread(Runnable target, String name) {
        super(target, name);
    }

    /**
     * Constructs an {@link EVOThread}.
     *
     * @param group the thread group
     * @param target the object whose {@code run} method is invoked when this thread is started
     * @param name the name of the new thread
     */
    public EVOThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    /**
     * Constructs an {@link EVOThread}.
     *
     * @param group the thread group
     * @param target the object whose {@code run} method is invoked when this thread is started
     * @param name the name of the new thread
     * @param stackSize the desired stack size for the new thread,
     *                  or zero to indicate that this parameter is to be ignored
     */
    public EVOThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }

    @Override
    public IRNG getRNG() {
        return rng;
    }
}
