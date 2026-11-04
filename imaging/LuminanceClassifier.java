package imaging;

import java.awt.Color;

public class LuminanceClassifier implements PixelClassifier {
    private final Picture image;
    private final double tau;
    private final boolean[][] foregroundPixels;

    public LuminanceClassifier(Picture image, double tau) {
        this.image = image;
        this.tau = tau;
        this.foregroundPixels = new boolean[image.width()][image.height()];
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
    public boolean[][] getForegroundPixels() {
        for (int col = 0; col < this.image.width(); col++) {
            for (int row = 0; row < this.image.height(); row++) {
                this.foregroundPixels[col][row] = this.isForeground(this.image.get(col, row));
            }
        }
        return this.foregroundPixels;
    }
}
