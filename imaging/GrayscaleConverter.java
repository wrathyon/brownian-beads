package imaging;

import java.awt.Color;

/**
 * Binarizes a picture to monochrome (white foreground, black background) using a {@link PixelClassifier}.
 */
public class GrayscaleConverter implements ImageConverter {
    private final PixelClassifier classifier;

    public GrayscaleConverter(PixelClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public Picture convert(Picture image) {
        if (image == null) {
            return null;
        }
        Picture grayscaled = new Picture(image.width(), image.height());
        boolean[][] foregroundPixels = this.classifier.getForegroundPixels(image);

        for (int col = 0; col < image.width(); col++) {
            for (int row = 0; row < image.height(); row++) {
                if (foregroundPixels[col][row]) {
                    grayscaled.set(col, row, Color.WHITE);
                } else {
                    grayscaled.set(col, row, Color.BLACK);
                }
            }
        }

        return grayscaled;
    }
}
