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
}
