package hr.fer.zemris.art;

/**
 * A {@link GrayScaleImage} provider that uses {@link ThreadLocal}.
 * Each thread has its own {@link GrayScaleImage} instance that it can access via {@link #getImage()}.
 *
 * @author Bruna Dujmović
 *
 */
public class GrayScaleImageProvider {

    /**
     * The width of the images to provide.
     */
    private int width;

    /**
     * The height of the images to provide.
     */
    private int height;

    /**
     * Thread-local {@link GrayScaleImage} instances each belonging to a specific thread.
     */
    private ThreadLocal<GrayScaleImage> threadLocal = new ThreadLocal<>();

    /**
     * Constructs a {@link GrayScaleImage}.
     *
     * @param width the width of the images to provide
     * @param height the height of the images to provide
     */
    public GrayScaleImageProvider(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the {@link GrayScaleImage} belonging to the current thread.
     *
     * @return the {@link GrayScaleImage} belonging to the current thread
     */
    public GrayScaleImage getImage() {
        GrayScaleImage image = threadLocal.get();

        if (image == null) {
            image = new GrayScaleImage(width, height);
            threadLocal.set(image);
        }

        return image;
    }
}
