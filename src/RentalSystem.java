package src;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Rental system.
 */
public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    private static RentalSystem instance;

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("src.Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("src.Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("src.Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("src.Vehicle is not rented.");
        }
    }    

    public void displayAvailableVehicles() {
        System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
        System.out.println("---------------------------------------------------------------------------------");
        
        for (Vehicle v : vehicles) {
            if (v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "src.Car          " : (v instanceof Motorcycle ? "src.Motorcycle   " : "src.Truck        ")) + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println("  " + v.getInfo());
        }
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }

    public Customer findCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getCustomerName().equalsIgnoreCase(name))
                return c;
        return null;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        
        return instance;
    }


    /**
     * Saves vehicle details to vehicles.txt
     *
     * @param vehicle The vvehicle information to be saved
     */
    private void saveVehicle(Vehicle vehicle) {
        try (FileWriter fileWriter = new FileWriter("storage/vehicles.txt", true)) {
            String info = "|     " + (vehicle instanceof Car ? "src.Car          " : (vehicle instanceof Motorcycle ? "src.Motorcycle   " : "src.Truck        ")) + vehicle.getInfo() + "\n";
            fileWriter.write(info);
        } catch(IOException e) {
            throw new RuntimeException("Failed to save vehicle details", e);
        }
    }

    /**
     * Saves customer details to customers.txt
     *
     * @param customer The customer information to be saved
     */
    private void saveCustomer(Customer customer) {

    }

    /**
     * Saves rental record details to rental_records.txt
     *
     * @param record The rental record information to be saved
     */
    private void saveRecord(RentalRecord record) {

    }
}
