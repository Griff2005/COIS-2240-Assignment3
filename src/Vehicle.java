package src;

/**
 * The type Vehicle.
 */
public abstract class Vehicle {
    /**
     * The License plate.
     */
    private String licensePlate;
    /**
     * The Make.
     */
    private String make;
    /**
     * The Model.
     */
    private String model;
    /**
     * The Year.
     */
    private int year;
    /**
     * The Status.
     */
    private VehicleStatus status;

    /**
     * The enum Vehicle status.
     */
    public enum VehicleStatus {
        /**
         * Available vehicle status.
         */
        AVAILABLE,
        /**
         * Reserved vehicle status.
         */
        RESERVED,
        /**
         * Rented vehicle status.
         */
        RENTED,
        /**
         * Maintenance vehicle status.
         */
        MAINTENANCE,
        /**
         * Outofservice vehicle status.
         */
        OUTOFSERVICE }

    /**
     * Instantiates a new Vehicle.
     *
     * @param make  the make
     * @param model the model
     * @param year  the year
     */
    protected Vehicle(String make, String model, int year) {
        this.make = (make == null || make.isEmpty()) ? null : capitalize(make);
        this.model = (model == null || model.isEmpty()) ? null : capitalize(model);
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }

    /**
     * Instantiates a new Vehicle.
     */
    protected Vehicle() {
        this(null, null, 0);
    }

    /**
     * Capitalize string.
     *
     * @param input the input
     * @return the string
     */
    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Is valid plate boolean.
     *
     * @param plate the plate
     * @return the boolean
     */
    private boolean isValidPlate(String plate) {
        return plate != null && !plate.isEmpty() && plate.matches("[a-zA-Z]{3}\\d{3}");
    }

    /**
     * Sets license plate.
     *
     * @param plate the plate
     */
    public void setLicensePlate(String plate) {
        if (!isValidPlate(plate))
            throw new IllegalArgumentException("Invalid license plate format. Must be 3 letters followed by 3 digits (e.g., ABC123).");
        this.licensePlate = plate.toUpperCase();
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    /**
     * Gets license plate.
     *
     * @return the license plate
     */
    public String getLicensePlate() { return licensePlate; }

    /**
     * Gets make.
     *
     * @return the make
     */
    public String getMake() { return make; }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() { return model;}

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() { return year; }

    /**
     * Gets status.
     *
     * @return the status
     */
    public VehicleStatus getStatus() { return status; }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

}
