package imaging;

import java.awt.Color;

public interface PixelClassifier {
    boolean isForeground(Color color);
    boolean[][] getForegroundPixels(Picture image);
}
