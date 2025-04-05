package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RentalSystemGUI extends Application {

    private RentalSystem rentalSystem = RentalSystem.getInstance();

    private ListView<String> vehicleList = new ListView<>();
    private ListView<String> customerList = new ListView<>();
    private ListView<String> rentalHistoryList = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vehicle Rental System");

        TabPane tabPane = new TabPane();

        // Tabs
        Tab addVehicleTab = new Tab("Add Vehicle", createAddVehiclePane());
        Tab addCustomerTab = new Tab("Add Customer", createAddCustomerPane());
        Tab rentVehicleTab = new Tab("Rent Vehicle", createRentVehiclePane());
        Tab returnVehicleTab = new Tab("Return Vehicle", createReturnVehiclePane());
        Tab displayTab = new Tab("Display", createDisplayPane());

        tabPane.getTabs().addAll(addVehicleTab, addCustomerTab, rentVehicleTab, returnVehicleTab, displayTab);

        primaryStage.setScene(new Scene(tabPane, 600, 400));
        primaryStage.show();
    }

    private VBox createAddVehiclePane() {
        TextField plateField = new TextField();
        TextField makeField = new TextField();
        TextField modelField = new TextField();
        TextField yearField = new TextField();
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Car", "Motorcycle", "Truck");
        TextField extraField = new TextField();
        Button addButton = new Button("Add Vehicle");

        addButton.setOnAction(e -> {
            try {
                String plate = plateField.getText().toUpperCase();
                String make = makeField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                String type = typeBox.getValue();
                Vehicle vehicle;

                if (type.equals("Car")) {
                    int seats = Integer.parseInt(extraField.getText());
                    vehicle = new Car(make, model, year, seats);
                } else if (type.equals("Motorcycle")) {
                    boolean hasSidecar = Boolean.parseBoolean(extraField.getText());
                    vehicle = new Motorcycle(make, model, year, hasSidecar);
                } else {
                    double cargoCapacity = Double.parseDouble(extraField.getText());
                    vehicle = new Truck(make, model, year, cargoCapacity);
                }

                vehicle.setLicensePlate(plate);
                rentalSystem.addVehicle(vehicle);
                updateVehicleList();
                showAlert("Vehicle added successfully.");
            } catch (Exception ex) {
                showAlert("Error adding vehicle: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Plate"), plateField, new Label("Make"), makeField,
                new Label("Model"), modelField, new Label("Year"), yearField,
                new Label("Type"), typeBox, new Label("Extra Info (Seats/Sidecar/Cargo)"), extraField, addButton);
    }

    private VBox createAddCustomerPane() {
        TextField idField = new TextField();
        TextField nameField = new TextField();
        Button addButton = new Button("Add Customer");

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                rentalSystem.addCustomer(new Customer(id, name));
                updateCustomerList();
                showAlert("Customer added successfully.");
            } catch (Exception ex) {
                showAlert("Error adding customer: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Customer ID"), idField, new Label("Name"), nameField, addButton);
    }

    private VBox createRentVehiclePane() {
        TextField plateField = new TextField();
        TextField customerField = new TextField();
        TextField amountField = new TextField();
        Button rentButton = new Button("Rent Vehicle");

        rentButton.setOnAction(e -> {
            try {
                Vehicle vehicle = rentalSystem.findVehicleByPlate(plateField.getText().toUpperCase());
                Customer customer = rentalSystem.findCustomerByName(customerField.getText());
                double amount = Double.parseDouble(amountField.getText());
                if (vehicle != null && customer != null) {
                    rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), amount);
                    updateVehicleList();
                    showAlert("Vehicle rented successfully.");
                } else {
                    showAlert("Vehicle or customer not found.");
                }
            } catch (Exception ex) {
                showAlert("Error renting vehicle: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Plate"), plateField, new Label("Customer Name"), customerField, new Label("Amount"), amountField, rentButton);
    }

    private VBox createReturnVehiclePane() {
        TextField plateField = new TextField();
        TextField customerField = new TextField();
        TextField feesField = new TextField();
        Button returnButton = new Button("Return Vehicle");

        returnButton.setOnAction(e -> {
            try {
                Vehicle vehicle = rentalSystem.findVehicleByPlate(plateField.getText().toUpperCase());
                Customer customer = rentalSystem.findCustomerByName(customerField.getText());
                double fees = Double.parseDouble(feesField.getText());
                if (vehicle != null && customer != null) {
                    rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), fees);
                    updateVehicleList();
                    showAlert("Vehicle returned successfully.");
                } else {
                    showAlert("Vehicle or customer not found.");
                }
            } catch (Exception ex) {
                showAlert("Error returning vehicle: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Plate"), plateField, new Label("Customer Name"), customerField, new Label("Fees"), feesField, returnButton);
    }

    private VBox createDisplayPane() {
        Button refreshButton = new Button("Refresh Lists");
        refreshButton.setOnAction(e -> {
            updateVehicleList();
            updateCustomerList();
            updateRentalHistoryList();
        });
        return new VBox(5, refreshButton, new Label("Available Vehicles"), vehicleList,
                new Label("Customers"), customerList,
                new Label("Rental History"), rentalHistoryList);
    }

    private void updateVehicleList() {
        vehicleList.getItems().clear();
        for (Vehicle v : rentalSystem.getVehicles()) {
            vehicleList.getItems().add(v.getInfo());
        }
    }

    private void updateCustomerList() {
        customerList.getItems().clear();
        for (Customer c : rentalSystem.getCustomers()) {
            customerList.getItems().add(c.toString());
        }
    }

    private void updateRentalHistoryList() {
        rentalHistoryList.getItems().clear();
        for (RentalRecord r : rentalSystem.getRentalHistory().getRecords()) {
            rentalHistoryList.getItems().add(r.toString());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
