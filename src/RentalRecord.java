package src;

import java.time.LocalDate;

/**
 * The type Rental record.
 */
public class RentalRecord {
    /**
     * The Vehicle.
     */
    private Vehicle vehicle;
    /**
     * The Customer.
     */
    private Customer customer;
    /**
     * The Record date.
     */
    private LocalDate recordDate;
    /**
     * The Total amount.
     */
    private double totalAmount;
    /**
     * The Record type.
     */
    private String recordType; // "RENT" or "RETURN"

    /**
     * Instantiates a new Rental record.
     *
     * @param vehicle     the vehicle
     * @param customer    the customer
     * @param recordDate  the record date
     * @param totalAmount the total amount
     * @param recordType  the record type
     */
    public RentalRecord(Vehicle vehicle, Customer customer, LocalDate recordDate, double totalAmount, String recordType) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.recordDate = recordDate;
        this.totalAmount = totalAmount;
        this.recordType = recordType;
    }

    /**
     * Get customer customer.
     *
     * @return the customer
     */
    public Customer getCustomer(){
    	return customer;
    }

    /**
     * Get vehicle vehicle.
     *
     * @return the vehicle
     */
    public Vehicle getVehicle(){
    	return vehicle;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return recordType + " | Plate: " + vehicle.getLicensePlate() + 
               " | src.Customer: " + customer.getCustomerName() + 
               " | Date: " + recordDate + 
               " | Amount: $" + totalAmount;
    }
}