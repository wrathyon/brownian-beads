package imaging;

import java.awt.Color;

public class GrayscaleConvertor implements ImageConvertor {
    private final Picture image;

    public GrayscaleConvertor(Picture image) {
        this.image = image;
    }

    @Override
    public Picture convert(boolean[][] whitePixels) {
        Picture grayscaled = new Picture(image.width(), image.height());

        for (int col = 0; col < image.width(); col++) {
            for (int row = 0; row < image.height(); row++) {
                if (whitePixels[col][row]) {
                    grayscaled.set(col, row, Color.WHITE);
                } else {
                    grayscaled.set(col, row, Color.BLACK);
                }
            }
        }
        
        return grayscaled;
    }
}
