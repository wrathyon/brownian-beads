package detecting;

import java.util.ArrayList;
import java.util.List;

import imaging.Picture;
import model.Blob;
import model.IBlob;

public class BlobDetector {
    private final Picture picture;
    private final boolean[][] foregroundPixels;
    private final boolean[][] isVisited;
    private final List<IBlob> blobs;

    public BlobDetector(Picture picture, boolean[][] foregroundPixels) {
        this.picture = picture;
        this.foregroundPixels = foregroundPixels;
        this.isVisited = new boolean[picture.width()][picture.height()];
        this.blobs = new ArrayList<>();
    }

    public List<IBlob> detectBlobs() {
        for (int col = 0; col < this.picture.width(); col++) {
            for (int row = 0; row < this.picture.height(); row++) {
                if (this.foregroundPixels[col][row] && !isVisited[col][row]) {
                    IBlob blob = new Blob();
                    this.dfs(col, row, blob);
                    this.blobs.add(blob);
                }
            }
        }
        return this.blobs;
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
}
