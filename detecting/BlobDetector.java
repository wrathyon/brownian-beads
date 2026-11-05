package detecting;

import java.util.ArrayList;
import java.util.List;

import model.Blob;
import model.IBlob;

public class BlobDetector {
    public List<IBlob> detect(boolean[][] foregroundPixels) {
        int width = foregroundPixels.length;
        int height = foregroundPixels[0].length;

        boolean[][] isVisited = new boolean[width][height];
        List<IBlob> blobs = new ArrayList<>();

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (foregroundPixels[col][row] && !isVisited[col][row]) {
                    IBlob blob = new Blob();
                    this.dfs(width, height, col, row, blob, foregroundPixels, isVisited);
                    blobs.add(blob);
                }
            }
        }

        return blobs;
    }

    private void dfs(int width, int height, int col, int row, IBlob blob,
                        boolean[][] foregroundPixels, boolean[][] isVisited) {
        if (col < 0 || col >= width || row < 0 || row >= height) {
            return;
        }
        if (isVisited[col][row] || !foregroundPixels[col][row]) {
            return;
        }

        isVisited[col][row] = true;
        blob.add(col, row);

        dfs(width, height, col + 1, row, blob, foregroundPixels, isVisited);
        dfs(width, height, col - 1, row, blob, foregroundPixels, isVisited);
        dfs(width, height, col, row + 1, blob, foregroundPixels, isVisited);
        dfs(width, height, col, row - 1, blob, foregroundPixels, isVisited);
    }
}
