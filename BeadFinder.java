import java.util.ArrayList;
import java.util.List;

import imaging.LuminanceClassifier;
import imaging.PixelClassifier;

public class BeadFinder {
    private final Picture picture;
    private final List<Blob> blobs;
    private final boolean[][] beads;
    private final boolean[][] isVisited;
    private final PixelClassifier classifier;

    public BeadFinder(Picture picture, double tau) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture cannot be null");
        }
        this.picture = picture;
        this.classifier = new LuminanceClassifier(tau);
        this.blobs = new ArrayList<>();

        int width = picture.width();
        int height = picture.height();
        this.beads = new boolean[width][height];
        this.isVisited = new boolean[width][height];

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                beads[col][row] = classifier.isForeground(picture.get(col, row));
            }
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (beads[col][row] && !isVisited[col][row]) {
                    Blob blob = new Blob();
                    dfs(col, row, blob);
                    blobs.add(blob);
                }
            }
        }
    }

    private void dfs(int col, int row, Blob blob) {
        if (col < 0 || col >= picture.width() || row < 0 || row >= picture.height()) {
            return;
        }
        if (isVisited[col][row] || !beads[col][row]) {
            return;
        }

        isVisited[col][row] = true;
        blob.add(col, row);

        dfs(col + 1, row, blob);
        dfs(col - 1, row, blob);
        dfs(col, row + 1, blob);
        dfs(col, row - 1, blob);
    }

    public Blob[] getBeads(int min) {
        List<Blob> filtered = new ArrayList<>();
        for (Blob blob : this.blobs) {
            if (blob.mass() >= min) {
                filtered.add(blob);
            }
        }
        return filtered.toArray(new Blob[0]);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java BeadFinder <min> <tau> <image_path>");
            return;
        }
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        String imagePath = args[2];

        Picture picture = new Picture(imagePath);
        BeadFinder bf = new BeadFinder(picture, tau);

        for (Blob blob : bf.getBeads(min)) {
            System.out.println(blob);
        }
    }
}
