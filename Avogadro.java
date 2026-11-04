import java.util.List;

import physics.PhysicalConstants;

public class Avogadro {
    private List<Double> displacements;
    
    public Avogadro(List<Double> displacements){
        this.displacements = displacements;
    }

    public double convertToMeter(double pixelDistance){
        return (PhysicalConstants.MPP*pixelDistance);
    }

    public double calculateD(){
        double D = 0;
        for (double displacement : this.displacements){
            D += Math.pow(convertToMeter(displacement),2);
        }
        return D/(2*this.displacements.size());
    }

    public double calculateBoltzmann() {
        return (6 * this.calculateD() * Math.PI * PhysicalConstants.VISCOSITY * PhysicalConstants.BEAD_RADIUS) / PhysicalConstants.T;
    }

    public double calculateAvogadro(){
        return PhysicalConstants.R / this.calculateBoltzmann();
    }
}
