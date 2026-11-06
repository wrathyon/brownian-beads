package physics;

/**
 * Physical and calibration constants for Brownian motion and the Stokes-Einstein relation.
 */
public final class PhysicalConstants {
    private PhysicalConstants() {}

    /** Absolute temperature of water in Kelvins (24°C). */
    public static final double TEMPERATURE = 297.0;

    /** Viscosity of water at 24°C in N·s/m² (or Pa·s). */
    public static final double VISCOSITY = 9.135E-4;

    /** Radius of a polystyrene bead in meters (0.5 μm). */
    public static final double BEAD_RADIUS = 0.5E-6;

    /** Conversion factor from pixels to meters (0.175 μm/pixel). */
    public static final double METERS_PER_PIXEL = 0.175E-6;

    /** Universal gas constant in J/(mol·K). */
    public static final double GAS_CONSTANT = 8.31446;
}
