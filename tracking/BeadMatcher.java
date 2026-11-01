package tracking;

import java.util.List;

import model.IBlob;

public interface BeadMatcher {
    List<Double> match(List<IBlob> current, List<IBlob> previous, int maxDistance);
}
