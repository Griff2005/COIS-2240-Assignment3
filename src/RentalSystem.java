package src;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * The type Rental system.
 */
public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    private static RentalSystem instance;

    private RentalSystem() {
        loadData();
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (findVehicleByPlate(vehicle.getLicensePlate()) == (null)) {
            vehicles.add(vehicle);
            saveVehicle(vehicle);
            return true;
        } else {
            System.out.println("Vehicle with licence plate '" + vehicle.getLicensePlate() + "' already exists.");
            return false;
        }
    }

    public boolean addCustomer(Customer customer) {
        if (findCustomerById(customer.getCustomerId()) == null) {
            customers.add(customer);
            saveCustomer(customer);
            return true;
        } else {
            System.out.println("Customer with ID '" + customer.getCustomerId() + "' already exists");
            return false;
        }
    }

    public boolean rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            System.out.println("src.Vehicle rented to " + customer.getCustomerName());
            saveRecord(record);
            return true;
        }
        else {
            System.out.println("src.Vehicle is not available for renting.");
            return false;
        }
    }

    public boolean returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            System.out.println("src.Vehicle returned by " + customer.getCustomerName());
            saveRecord(record);
            return true;
        }
        else {
            System.out.println("src.Vehicle is not rented.");
            return false;
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
        try (FileWriter fileWriter = new FileWriter("storage/customers.txt", true)) {
            String info = customer.toString() + "\n";
            fileWriter.write(info);
        } catch(IOException e) {
            throw new RuntimeException("Failed to save customer details", e);
        }
    }

    /**
     * Saves rental record details to rental_records.txt
     *
     * @param record The rental record information to be saved
     */
    private void saveRecord(RentalRecord record) {
        try (FileWriter fileWriter = new FileWriter("storage/rental_records.txt", true)) {
            String info = record.toString() + "\n";
            fileWriter.write(info);
        } catch(IOException e) {
            throw new RuntimeException("Failed to save rental record details", e);
        }
    }
    
    private void loadData() {
        loadVehicles();
        loadCustomers();
        loadRentalRecords();
    }

    private void loadVehicles() {
        File file = new File("storage/vehicles.txt");
        if (!file.exists()) return;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (line.isEmpty()) continue;  // Skip empty lines

                String[] rawParts = line.split("\\|");
                List<String> partsList = new ArrayList<>();

                // Clean the split results: remove empty elements
                for (String part : rawParts) {
                    part = part.trim();
                    if (!part.isEmpty()) {
                        partsList.add(part);
                    }
                }

                if (partsList.size() < 7) {  // Now safe to check 7 parts (type, plate, make, model, year, status, extra info)
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String type = partsList.get(0);     // src.Car, src.Motorcycle
                String plate = partsList.get(1);
                String make = partsList.get(2);
                String model = partsList.get(3);
                int year = Integer.parseInt(partsList.get(4));
                Vehicle.VehicleStatus status = Vehicle.VehicleStatus.valueOf(partsList.get(5));
                String extraInfo = partsList.get(6); // Seats: 5 or Sidecar: Yes

                // Create object normally
                Vehicle vehicle;
                if (type.contains("Car")) {
                    int seats = Integer.parseInt(extraInfo.split(":")[1].trim());
                    vehicle = new Car(make, model, year, seats);
                } else if (type.contains("Motorcycle")) {
                    boolean hasSidecar = extraInfo.split(":")[1].trim().equalsIgnoreCase("Yes");
                    vehicle = new Motorcycle(make, model, year, hasSidecar);
                } else {
                    continue;
                }
                vehicle.setLicensePlate(plate);
                vehicle.setStatus(status);
                vehicles.add(vehicle);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load vehicles", e);
        }
    }

        private void loadCustomers() {
            File file = new File("storage/customers.txt");
            if (!file.exists()) return;

            try (Scanner scan = new Scanner(file)) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts.length < 2) continue;

                    int id = Integer.parseInt(parts[0].split(":")[1].trim());
                    String name = parts[1].split(":")[1].trim();

                    customers.add(new Customer(id, name));
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load customers", e);
            }
        }

    private void loadRentalRecords() {
        File file = new File("storage/rental_records.txt");
        if (!file.exists()) return;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;

                String recordType = parts[0].trim();
                String plate = parts[1].split(":")[1].trim();
                String customerName = parts[2].split(":")[1].trim();
                LocalDate date = LocalDate.parse(parts[3].split(":")[1].trim());
                double amount = Double.parseDouble(parts[4].split("\\$")[1].trim());

                Vehicle vehicle = findVehicleByPlate(plate);
                Customer customer = findCustomerByName(customerName);

                if (vehicle != null && customer != null) {
                    rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, recordType));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rental records", e);
        }
    }
}
