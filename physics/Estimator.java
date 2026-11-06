package physics;

import java.util.List;

/**
 * Computes diffusion variance and estimates Boltzmann's constant and Avogadro's number
 * via Einstein's Brownian motion relation and the Stokes-Einstein equation.
 */
public class Estimator {
    private final List<Double> displacements;

    public Estimator(List<Double> displacements) {
        this.displacements = displacements;
    }

    private double convertToMeter(double pixelDistance) {
        return PhysicalConstants.METERS_PER_PIXEL * pixelDistance;
    }

    /** Calculates the self-diffusion coefficient D = sum(r^2) / (2 * N). */
    public double calculateDiffusion() {
        if (this.displacements == null || this.displacements.isEmpty()) {
            return Double.NaN;
        }

        double D = 0.0;

        for (double displacement : this.displacements) {
            double r = convertToMeter(displacement);
            D += r * r;
        }

        return D / (2 * this.displacements.size());
    }

    /** Estimates Boltzmann's constant: k = 6 * pi * eta * rho * D / T. */
    public double calculateBoltzmann() {
        return (
            6 * this.calculateDiffusion() *
            Math.PI * PhysicalConstants.VISCOSITY *
            PhysicalConstants.BEAD_RADIUS
        ) / PhysicalConstants.TEMPERATURE;
    }

    /** Estimates Avogadro's number: N_A = R / k. */
    public double calculateAvogadro() {
        return PhysicalConstants.GAS_CONSTANT / this.calculateBoltzmann();
    }
}
