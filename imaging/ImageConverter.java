package imaging;

/**
 * Strategy interface for image transformation.
 */
public interface ImageConverter {
    /** Transforms the input picture into a new converted picture. */
    Picture convert(Picture image);
}
