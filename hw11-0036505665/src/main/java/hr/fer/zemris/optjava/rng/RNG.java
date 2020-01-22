package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

/**
 * A singleton class for providing {@link IRNG} instances.
 *
 * @author Bruna Dujmović
 * 
 */
public class RNG {

    /**
     * The name of the resource.
     */
    private static final String RNG_CONFIG_PROPERTIES = "rng-config.properties";

    /**
     * The provider to load.
     */
    private static IRNGProvider rngProvider;

    static {
        Properties properties = new Properties();
        ClassLoader rngClassLoader = RNG.class.getClassLoader();

        try {
            properties.load(Objects.requireNonNull(rngClassLoader.getResourceAsStream(RNG_CONFIG_PROPERTIES)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String className = properties.getProperty("rng-provider");
        try {
            rngProvider = (IRNGProvider) rngClassLoader.loadClass(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private constructor.
     */
    private RNG() {}

    /**
     * Returns an {@link IRNG} instance.
     *
     * @return an {@link IRNG} instance
     */
    public static IRNG getRNG() {
        return rngProvider.getRNG();
    }
}