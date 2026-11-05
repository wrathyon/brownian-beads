package physics;

import java.util.List;

public class Estimator {
    private final List<Double> displacements;

    public Estimator(List<Double> displacements) {
        this.displacements = displacements;
    }

    private double convertToMeter(double pixelDistance) {
        return PhysicalConstants.METERS_PER_PIXEL * pixelDistance;
    }

    private double calculateDiffusion() {
        double D = 0.0;

        for (double displacement : this.displacements) {
            D += Math.pow(convertToMeter(displacement), 2);
        }

        return D / (2 * this.displacements.size());
    }

    public double calculateBoltzmann() {
        return (
            6 * this.calculateDiffusion() *
            Math.PI * PhysicalConstants.VISCOSITY *
            PhysicalConstants.BEAD_RADIUS
        ) / PhysicalConstants.TEMPERATURE;
    }

    public double calculateAvogadro() {
        return PhysicalConstants.GAS_CONSTANT / this.calculateBoltzmann();
    }
}
