package src;

/**
 * The type Motorcycle.
 */
public class Motorcycle extends Vehicle implements Rentable {
    /**
     * The Has sidecar.
     */
    private boolean hasSidecar;

    /**
     * Instantiates a new Motorcycle.
     *
     * @param make       the make
     * @param model      the model
     * @param year       the year
     * @param hasSidecar the has sidecar
     */
    public Motorcycle(String make, String model, int year, boolean hasSidecar) {
        super(make, model, year);
        this.hasSidecar = hasSidecar;
    }

    /**
     * Has sidecar boolean.
     *
     * @return the boolean
     */
    public boolean hasSidecar() {
        return hasSidecar;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " | Sidecar: " + (hasSidecar ? "Yes" : "No");
    }

    /**
     * Rent vehicle.
     */
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.RENTED);
        System.out.println("src.Motorcycle " + getLicensePlate() + " has been rented.");
    }

    /**
     * Return vehicle.
     */
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.AVAILABLE);
        System.out.println("src.Motorcycle " + getLicensePlate() + " has been returned.");
    }
}