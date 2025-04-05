package src;

/**
 * The type Sport car.
 */
public final class SportCar extends Car {
    /**
     * The Horsepower.
     */
    private int horsepower;
    /**
     * The Has turbo.
     */
    private boolean hasTurbo;

    /**
     * Instantiates a new Sport car.
     *
     * @param make       the make
     * @param model      the model
     * @param year       the year
     * @param numSeats   the num seats
     * @param horsepower the horsepower
     * @param hasTurbo   the has turbo
     */
    public SportCar(String make, String model, int year, int numSeats, int horsepower, boolean hasTurbo) {
        super(make, model, year, numSeats);
        this.horsepower = horsepower;
        this.hasTurbo = hasTurbo;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " | Horsepower: " + horsepower + " | Turbo: " + (hasTurbo ? "Yes" : "No");
    }
}