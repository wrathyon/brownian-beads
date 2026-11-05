package imaging;

import java.awt.Color;

public class GrayscaleConvertor implements ImageConvertor {
    private final PixelClassifier classifier;

    public GrayscaleConvertor(PixelClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public Picture convert(Picture image) {
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
