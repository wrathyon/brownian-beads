public class Blob {
    private int sumX;
    private int sumY;
    private int mass;

    public Blob() {
        this.sumX = 0;
        this.sumY = 0;
        this.mass = 0;
    }

    public int getSumX() {
        return this.sumX;
    }

    public int getSumY() {
        return this.sumY;
    }

    public int mass() {
        return this.mass;
    }

    public void add(int x, int y) {
        this.sumX += x;
        this.sumY += y;
        this.mass += 1;
    }

    public double distanceTo(Blob that) {
        if (that == null || this.mass == 0 || that.mass == 0) {
            return Double.NaN;
        }
        double dx = (double) this.sumX / this.mass - (double) that.sumX / that.mass;
        double dy = (double) this.sumY / this.mass - (double) that.sumY / that.mass;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        if (this.mass == 0) {
            return String.format(java.util.Locale.US, "%2d (%8.4f, %8.4f)", 0, 0.0, 0.0);
        }
        return String.format(
            java.util.Locale.US,
            "%2d (%8.4f, %8.4f)",
            this.mass,
            (double) this.sumX / this.mass,
            (double) this.sumY / this.mass
        );
    }

    public static void main(String[] args) {
        Blob b1 = new Blob();
        b1.add(0, 0);
        b1.add(1, 1);
        System.out.println("b1: " + b1);
        System.out.println("b1 mass: " + b1.mass());

        Blob b2 = new Blob();
        b2.add(3, 4);
        System.out.println("b2: " + b2);
        System.out.printf(java.util.Locale.US, "Distance: %.4f%n", b1.distanceTo(b2));
    }
}
