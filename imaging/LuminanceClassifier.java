package imaging;

import java.awt.Color;

public class LuminanceClassifier implements PixelClassifier {
    private final double tau;

    public LuminanceClassifier(double tau) {
        this.tau = tau;
    }

    @Override 
    public boolean isForeground(Color color) {
        if (color == null) {
            return false;
        }

        double y = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
        return y >= tau;
    }

    @Override
    public boolean[][] getForegroundPixels(Picture image) {
        boolean[][] foregroundPixels = new boolean[image.width()][image.height()];

        for (int col = 0; col < image.width(); col++) {
            for (int row = 0; row < image.height(); row++) {
                foregroundPixels[col][row] = this.isForeground(image.get(col, row));
            }
        }

        return foregroundPixels;
    }
}
