package model;

/**
 * Represents a 2D particle/blob characterized by mass and center-of-mass coordinates.
 */
public interface IBlob {
    /** Returns the x-coordinate of the centroid. */
    double getCenterX();

    /** Returns the y-coordinate of the centroid. */
    double getCenterY();

    /** Returns the number of pixels in this blob. */
    int mass();

    /** Accumulates pixel coordinates (x, y) into the blob. */
    void add(double x, double y);

    /** Returns the Euclidean distance between the centroids of this and that blob. */
    double distanceTo(IBlob that);
}
