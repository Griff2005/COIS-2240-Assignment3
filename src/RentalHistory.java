package src;

import java.util.List;
import java.util.ArrayList;

/**
 * The type Rental history.
 */
public class RentalHistory {
    /**
     * The Rental records.
     */
    private List<RentalRecord> rentalRecords = new ArrayList<>();

    /**
     * Add record.
     *
     * @param record the record
     */
    public void addRecord(RentalRecord record) {
        rentalRecords.add(record);
    }

    /**
     * Gets rental history.
     *
     * @return the rental history
     */
    public List<RentalRecord> getRentalHistory() {
        return rentalRecords;
    }

    /**
     * Gets rental records by customer.
     *
     * @param customerName the customer name
     * @return the rental records by customer
     */
    public List<RentalRecord> getRentalRecordsByCustomer(String customerName) {
        List<RentalRecord> result = new ArrayList<>();
        for (RentalRecord record : rentalRecords) {
            if (record.getCustomer().toString().toLowerCase().contains(customerName.toLowerCase())) {
                result.add(record);
            }
        }
        return result;
    }

    /**
     * Gets rental records by vehicle.
     *
     * @param licensePlate the license plate
     * @return the rental records by vehicle
     */
    public List<RentalRecord> getRentalRecordsByVehicle(String licensePlate) {
        List<RentalRecord> result = new ArrayList<>();
        for (RentalRecord record : rentalRecords) {
            if (record.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                result.add(record);
            }
        }
        return result;
    }

    public List<RentalRecord> getRecords() {
        return rentalRecords;
    }
}