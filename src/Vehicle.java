package src;

public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUTOFSERVICE }

    protected Vehicle(String make, String model, int year) {
        this.make = (make == null || make.isEmpty()) ? null : capitalize(make);
        this.model = (model == null || model.isEmpty()) ? null : capitalize(model);
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }
    
    protected Vehicle() {
        this(null, null, 0);
    }
    
    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    
    private boolean isValidPlate(String plate) {
        return plate != null && !plate.isEmpty() && plate.matches("[a-zA-Z]{3}\\d{3}");
    }
    
    public void setLicensePlate(String plate) {
        if (!isValidPlate(plate))
            throw new IllegalArgumentException("Invalid license plate format. Must be 3 letters followed by 3 digits (e.g., ABC123).");
        this.licensePlate = plate.toUpperCase();
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

}
