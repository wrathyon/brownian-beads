package imaging;

import java.awt.Color;

/**
 * Strategy interface for identifying foreground pixels in microscopy images.
 */
public interface PixelClassifier {
    /** Evaluates whether the specified color is classified as foreground. */
    boolean isForeground(Color color);

    /** Extracts a 2D boolean grid where true marks foreground pixels in the image. */
    boolean[][] getForegroundPixels(Picture image);
}
