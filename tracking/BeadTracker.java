package tracking;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

public class BeadTracker {
    private final List<List<IBlob>> blobsList;
    private final BeadMatcher beadMatcher;

    public BeadTracker(List<List<IBlob>> blobsList) {
        this.blobsList = blobsList;
        this.beadMatcher = new NearestNeighborMatcher();
    }

    public List<List<Double>> trackDistance(double maxDistance) {
        List<List<Double>> displacements = new ArrayList<>();

        for (int i = 0; i < blobsList.size() - 1; i++) {
            displacements.add(
                beadMatcher.matchDistance(this.blobsList.get(i + 1), this.blobsList.get(i), maxDistance)
            );
        }

        return displacements;
    }
}
