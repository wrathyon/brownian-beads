package detecting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.IBlob;

/**
 * Utility class to filter blobs by minimum mass (pixel count).
 */
public final class BeadFinder {
    private BeadFinder() {}

    /** Filters and returns only blobs with mass >= min. */
    public static List<IBlob> filter(List<IBlob> blobs, int min) {
        if (blobs == null) {
            return Collections.emptyList();
        }
        List<IBlob> filtered = new ArrayList<>();
        for (IBlob blob : blobs) {
            if (blob != null && blob.mass() >= min) {
                filtered.add(blob);
            }
        }
        return filtered;
    }
}
