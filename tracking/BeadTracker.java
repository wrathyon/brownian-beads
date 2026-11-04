package tracking;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

public class BeadTracker {
    private final List<List<IBlob>> blobsList;
    private final BeadMatcher bm;

    public BeadTracker(List<List<IBlob>> blobsList, double maxDistance){
        this.blobsList = blobsList;
        this.bm = new NearestNeighborMatcher(maxDistance);
    }

    public List<List<Double>> trackDistance() {
        List<List<Double>> displacements = new ArrayList<>();
        for (int i = 0; i < blobsList.size() - 1; i++) {
            displacements.add(bm.match(this.blobsList.get(i + 1), this.blobsList.get(i)));
        }
        return displacements;
    }
}
