package detecting;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

public class BeadFinder {
    private final List<IBlob> blobs;

    public BeadFinder(List<IBlob> blobs) {
        this.blobs = blobs;
    }

    public List<IBlob> findBeads(int min) {
        List<IBlob> filtered = new ArrayList<>();
        for (IBlob blob : this.blobs) {
            if (blob.mass() >= min) {
                filtered.add(blob);
            }
        }
        return filtered;
    }
}
