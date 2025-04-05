package src;

/**
 * The type Truck.
 */
public class Truck extends Vehicle implements Rentable {
    /**
     * The Cargo capacity.
     */
    private double cargoCapacity;

    /**
     * Instantiates a new Truck.
     *
     * @param make          the make
     * @param model         the model
     * @param year          the year
     * @param cargoCapacity the cargo capacity
     */
    public Truck(String make, String model, int year, double cargoCapacity) {
        super(make, model, year);
        if (cargoCapacity <= 0) throw new IllegalArgumentException("Cargo capacity must be > 0");
        this.cargoCapacity = cargoCapacity;
    }

    /**
     * Gets cargo capacity.
     *
     * @return the cargo capacity
     */
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " | Cargo Capacity: " + cargoCapacity;
    }

    /**
     * Rent vehicle.
     */
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.RENTED);
        System.out.println("src.Truck " + getLicensePlate() + " has been rented.");
    }

    /**
     * Return vehicle.
     */
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.AVAILABLE);
        System.out.println("src.Truck " + getLicensePlate() + " has been returned.");
    }
}