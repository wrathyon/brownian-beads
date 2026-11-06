package tracking;

import java.util.List;

import model.IBlob;

/**
 * Strategy interface for matching beads across consecutive frames.
 */
public interface BeadMatcher {
    /** Matches beads from current frame to previous frame, returning displacement distances < maxDistance. */
    List<Double> matchDistance(List<IBlob> current, List<IBlob> previous, double maxDistance);
}
