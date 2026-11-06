package tracking;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

/**
 * Greedy nearest-neighbor matcher associating each bead in current frame to the nearest bead in previous frame.
 */
public class NearestNeighborMatcher implements BeadMatcher {
    @Override
    public List<Double> matchDistance(List<IBlob> current, List<IBlob> previous, double maxDistance) {
        if (current == null || previous == null || current.isEmpty() || previous.isEmpty()) {
            return new ArrayList<>();
        }

        List<Double> displacements = new ArrayList<>();

        for (int i = 0; i < current.size(); i++) {
            double minDistance = Double.MAX_VALUE;

            for (int j = 0; j < previous.size(); j++) {                
                double distance = current.get(i).distanceTo(previous.get(j));

                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

            if (minDistance < maxDistance) {
                displacements.add(minDistance);
            }
        }

        return displacements;
    }
}
