import java.util.ArrayList;
import java.util.List;

import model.IBlob;
import model.Blob;
import imaging.LuminanceClassifier;
import imaging.Picture;
import imaging.PixelClassifier;

public class BeadFinder {
    private final Picture picture;
    private final List<IBlob> blobs;
    private final boolean[][] foregroundPixels;
    private final boolean[][] isVisited;
    private final PixelClassifier classifier;

    public BeadFinder(Picture picture, double tau) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture cannot be null");
        }
        this.picture = picture;
        this.classifier = new LuminanceClassifier(tau);
        this.blobs = new ArrayList<>();

        this.foregroundPixels = new boolean[picture.width()][picture.height()];
        this.isVisited = new boolean[picture.width()][picture.height()];
    }

    public void detectBlobs() {
        for (int col = 0; col < this.picture.width(); col++) {
            for (int row = 0; row < this.picture.height(); row++) {
                this.foregroundPixels[col][row] = this.classifier.isForeground(this.picture.get(col, row));
            }
        }

        for (int col = 0; col < this.picture.width(); col++) {
            for (int row = 0; row < this.picture.height(); row++) {
                if (this.foregroundPixels[col][row] && !isVisited[col][row]) {
                    IBlob blob = new Blob();
                    this.dfs(col, row, blob);
                    this.blobs.add(blob);
                }
            }
        }
    }

    private void dfs(int col, int row, IBlob blob) {
        if (col < 0 || col >= this.picture.width() || row < 0 || row >= this.picture.height()) {
            return;
        }
        if (this.isVisited[col][row] || !this.foregroundPixels[col][row]) {
            return;
        }

        this.isVisited[col][row] = true;
        blob.add(col, row);

        dfs(col + 1, row, blob);
        dfs(col - 1, row, blob);
        dfs(col, row + 1, blob);
        dfs(col, row - 1, blob);
    }

    public List<IBlob> getBeads(int min) {
        List<IBlob> filtered = new ArrayList<>();
        for (IBlob blob : this.blobs) {
            if (blob.mass() >= min) {
                filtered.add(blob);
            }
        }
        return filtered;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java BeadFinder <min> <tau> <image_path>");
            return;
        }
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        String imagePath = args[2];

        Picture image = new Picture(imagePath);
        BeadFinder bf = new BeadFinder(image, tau);
        bf.detectBlobs();

        for (IBlob blob : bf.getBeads(min)) {
            System.out.println(blob);
        }
    }
}
