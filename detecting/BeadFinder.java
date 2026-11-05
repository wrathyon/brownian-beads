package detecting;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

public class BeadFinder {
    public static List<IBlob> filter(List<IBlob> blobs, int min) {
        List<IBlob> filtered = new ArrayList<>();
        for (IBlob blob : blobs) {
            if (blob.mass() >= min) {
                filtered.add(blob);
            }
        }
        return filtered;
    }
}
