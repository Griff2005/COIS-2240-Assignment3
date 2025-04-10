package src;

import java.util.Scanner;
import java.time.LocalDate;

/**
 * The type Vehicle rental app.
 */
public class VehicleRentalApp {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RentalSystem rentalSystem = RentalSystem.getInstance();

        while (true) {
        	System.out.println("1: Add src.Vehicle\n2: Add src.Customer\n3: Rent src.Vehicle\n4: Return src.Vehicle\n5: Display Available Vehicles\n6: Show Rental History\n7: Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("  1: src.Car\n  2: src.Motorcycle\n  3: src.Truck");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter license plate: ");
                    String plate = scanner.nextLine().toUpperCase();
                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();
                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();

                    Vehicle vehicle;
                    if (type == 1) {
                        System.out.print("Enter number of seats: ");
                        int seats = scanner.nextInt();
                        vehicle = new Car(make, model, year, seats);
                        System.out.println("src.Car added successfuly.");
                    } else if (type == 2) {
                        System.out.print("Has sidecar? (true/false): ");
                        boolean sidecar = scanner.nextBoolean();
                        vehicle = new Motorcycle(make, model, year, sidecar);
                        System.out.println("src.Motorcycle added successfuly.");
                    } else if (type == 3) {
                        System.out.print("Enter the cargo capacity: ");
                        double cargoCapacity = scanner.nextDouble();
                        vehicle = new Truck(make, model, year, cargoCapacity);
                        System.out.println("src.Truck added successfuly.");
                    } else {
                        vehicle = null;
                    }
                    
                    if (vehicle != null){
                        try {
                            vehicle.setLicensePlate(plate);
                            rentalSystem.addVehicle(vehicle);
                            System.out.println("src.Vehicle added.");
                        } catch(IllegalArgumentException e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println("src.Vehicle not added.");
                    }
                    break;

                case 2:
                    System.out.print("Enter customer ID: ");
                    int cid = scanner.nextInt();
                    System.out.print("Enter name: ");
                    scanner.nextLine();
                    String cname = scanner.nextLine();

                    rentalSystem.addCustomer(new Customer(cid, cname));
                    System.out.println("src.Customer added.");
                    break;
                    
                case 3: 
                    System.out.println("List of Vehicles:");
                    rentalSystem.displayAvailableVehicles();

                    System.out.print("Enter license plate: ");
                    String rentPlate = scanner.nextLine().toUpperCase();

                    System.out.println("Registered Customers:");
                    rentalSystem.displayAllCustomers();

                    System.out.print("Enter customer name: ");
                    String cnameRent = scanner.nextLine();

                    System.out.print("Enter rental amount: ");
                    double rentAmount = scanner.nextDouble();
                    scanner.nextLine();

                    Vehicle vehicleToRent = rentalSystem.findVehicleByPlate(rentPlate);
                    Customer customerToRent = rentalSystem.findCustomerByName(cnameRent);

                    if (vehicleToRent == null || customerToRent == null) {
                        System.out.println("src.Vehicle or customer not found.");
                        break;
                    }

                    rentalSystem.rentVehicle(vehicleToRent, customerToRent, LocalDate.now(), rentAmount);
                    break;

                case 4:
                    System.out.println("List of Vehicles:");
                    rentalSystem.displayAvailableVehicles();
                    
                    System.out.print("Enter license plate: ");
                    String returnPlate = scanner.nextLine().toUpperCase();
                    
                    System.out.println("Registered Customers:");
                    rentalSystem.displayAllCustomers();

                    System.out.print("Enter customer name: ");
                    String cnameReturn = scanner.nextLine();

                    System.out.print("Enter return fees: ");
                    double returnFees = scanner.nextDouble();
                    scanner.nextLine();

                    Vehicle vehicleToReturn = rentalSystem.findVehicleByPlate(returnPlate);
                    Customer customerToReturn = rentalSystem.findCustomerByName(cnameReturn);

                    if (vehicleToReturn == null || customerToReturn == null) {
                        System.out.println("src.Vehicle or customer not found.");
                        break;
                    }

                    rentalSystem.returnVehicle(vehicleToReturn, customerToReturn, LocalDate.now(), returnFees);
                    break;
                    
                case 5:
                    rentalSystem.displayAvailableVehicles();
                    break;
                
                case 6:
                    System.out.println("Rental History:");
                    rentalSystem.displayRentalHistory();
                    break;
                    
                case 7: 
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}