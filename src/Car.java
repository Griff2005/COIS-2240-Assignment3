package src;

/**
 * The type Car.
 */
public class Car extends Vehicle implements Rentable {
    /**
     * The Num seats.
     */
    private int numSeats;

    /**
     * Instantiates a new Car.
     *
     * @param make     the make
     * @param model    the model
     * @param year     the year
     * @param numSeats the num seats
     */
    public Car(String make, String model, int year, int numSeats) {
        super(make, model, year);
        this.numSeats = numSeats;
    }

    /**
     * Gets num seats.
     *
     * @return the num seats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " | Seats: " + numSeats;
    }

    /**
     * Rent vehicle.
     */
    @Override
    public void rentVehicle() {
        setStatus(VehicleStatus.RENTED);
        System.out.println("src.Car " + getLicensePlate() + " has been rented.");
    }

    /**
     * Return vehicle.
     */
    @Override
    public void returnVehicle() {
        setStatus(VehicleStatus.AVAILABLE);
        System.out.println("src.Car " + getLicensePlate() + " has been returned.");
    }
}