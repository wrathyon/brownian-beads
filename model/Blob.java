package model;

/**
 * Accumulator implementation of {@link IBlob} tracking center-of-mass on demand.
 */
public class Blob implements IBlob {
    private double sumX;
    private double sumY;
    private int mass;

    public Blob() {
        this.sumX = 0.0;
        this.sumY = 0.0;
        this.mass = 0;
    }

    @Override
    public double getCenterX() {
        if (this.mass == 0) {
            return Double.NaN;
        }
        return this.sumX / this.mass;
    }

    @Override
    public double getCenterY() {
        if (this.mass == 0) {
            return Double.NaN;
        }
        return this.sumY / this.mass;
    }

    @Override
    public int mass() {
        return this.mass;
    }

    @Override
    public void add(double x, double y) {
        this.sumX += x;
        this.sumY += y;
        this.mass += 1;
    }

    @Override
    public double distanceTo(IBlob that) {
        if (that == null || this.mass == 0 || that.mass() == 0) {
            return Double.NaN;
        }
        double dx = this.getCenterX() - that.getCenterX();
        double dy = this.getCenterY() - that.getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        if (this.mass == 0) {
            return String.format("%2d (%8.4f, %8.4f)", 0, 0.0, 0.0);
        }
        return String.format(
            "%2d (%8.4f, %8.4f)",
            this.mass,
            this.getCenterX(),
            this.getCenterY()
        );
    }
}
