package tracking;

import java.util.List;

import model.IBlob;

public interface BeadMatcher {
    List<Double> matchDistance(List<IBlob> current, List<IBlob> previous, double maxDistance);
}
