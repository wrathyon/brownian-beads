package tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.IBlob;

/**
 * Tracks particle displacements across consecutive video frame pairs using an injected {@link BeadMatcher}.
 */
public class BeadTracker {
    private final List<List<IBlob>> blobsList;
    private final BeadMatcher beadMatcher;

    public BeadTracker(List<List<IBlob>> blobsList, BeadMatcher beadMatcher) {
        this.blobsList = blobsList;
        this.beadMatcher = beadMatcher;
    }

    /** Tracks bead displacements for each consecutive frame pair. */
    public List<List<Double>> trackDistance(double maxDistance) {
        if (blobsList == null || blobsList.size() < 2) {
            return Collections.emptyList();
        }

        List<List<Double>> displacements = new ArrayList<>();

        for (int i = 0; i < blobsList.size() - 1; i++) {
            displacements.add(
                beadMatcher.matchDistance(this.blobsList.get(i + 1), this.blobsList.get(i), maxDistance)
            );
        }

        return displacements;
    }
}
