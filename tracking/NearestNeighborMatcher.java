package tracking;

import java.util.ArrayList;
import java.util.List;

import model.IBlob;

public class NearestNeighborMatcher implements BeadMatcher {
    @Override
    public List<Double> match(List<IBlob> current, List<IBlob> previous, int maxDistance) {
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
