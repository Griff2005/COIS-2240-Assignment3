package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * RentalSystemGUI is the main class for the vehicle rental management system.
 * It provides a JavaFX-based graphical user interface to add vehicles, add customers,
 * rent vehicles, return vehicles, and view rental history.
 */
public class RentalSystemGUI extends Application {

    /** Singleton instance of the rental system */
    private RentalSystem rentalSystem = RentalSystem.getInstance();

    /** ListView to display available vehicles */
    private ListView<String> vehicleList = new ListView<>();

    /** ListView to display registered customers */
    private ListView<String> customerList = new ListView<>();

    /** ListView to display rental history */
    private ListView<String> rentalHistoryList = new ListView<>();

    /**
     * Entry point for the JavaFX application.
     * @param primaryStage the main stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vehicle Rental System");

        TabPane tabPane = new TabPane();

        // Create tabs for different functionalities
        Tab addVehicleTab = new Tab("Add Vehicle", createAddVehiclePane());
        Tab addCustomerTab = new Tab("Add Customer", createAddCustomerPane());
        Tab rentVehicleTab = new Tab("Rent Vehicle", createRentVehiclePane());
        Tab returnVehicleTab = new Tab("Return Vehicle", createReturnVehiclePane());
        Tab displayTab = new Tab("Display", createDisplayPane());
        Tab exitTab = new Tab("Exit", createExitPane(primaryStage));

        tabPane.getTabs().addAll(addVehicleTab, addCustomerTab, rentVehicleTab, returnVehicleTab, displayTab, exitTab);

        tabPane.getTabs().forEach(tab -> tab.setClosable(false)); // Make tabs unclosable
        tabPane.getSelectionModel().select(displayTab); // Set default tab to display

        primaryStage.setScene(new Scene(tabPane, 800, 500));
        primaryStage.show();

        updateAllLists(); // Load initial data
    }

    /**
     * Creates the pane for adding a new vehicle.
     * @return VBox containing input fields and button
     */
    private VBox createAddVehiclePane() {
        TextField plateField = new TextField();
        plateField.setPromptText("e.g., ABC123");
        TextField makeField = new TextField();
        makeField.setPromptText("e.g., Toyota");
        TextField modelField = new TextField();
        modelField.setPromptText("e.g., Corolla");
        TextField yearField = new TextField();
        yearField.setPromptText("e.g., 2020");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Car", "Motorcycle", "Truck");
        TextField extraField = new TextField();
        extraField.setPromptText("Seats / Sidecar / Cargo Capacity");
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
                updateAllLists();
                showAlert("Vehicle added successfully.");

                plateField.clear();
                makeField.clear();
                modelField.clear();
                yearField.clear();
                typeBox.setValue(null);
                extraField.clear();

            } catch (Exception ex) {
                showAlert("Error adding vehicle: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Plate"), plateField, new Label("Make"), makeField,
                new Label("Model"), modelField, new Label("Year"), yearField,
                new Label("Type"), typeBox, new Label("Extra Info (Seats/Sidecar/Cargo)"), extraField, addButton);
    }

    /**
     * Creates the pane for adding a new customer.
     * @return VBox containing input fields and button
     */
    private VBox createAddCustomerPane() {
        TextField idField = new TextField();
        idField.setPromptText("e.g., 1001");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., John Doe");
        Button addButton = new Button("Add Customer");

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                rentalSystem.addCustomer(new Customer(id, name));
                updateAllLists();
                showAlert("Customer added successfully.");

                idField.clear();
                nameField.clear();

            } catch (Exception ex) {
                showAlert("Error adding customer: " + ex.getMessage());
            }
        });

        return new VBox(5, new Label("Customer ID"), idField, new Label("Name"), nameField, addButton);
    }

    /**
     * Creates the pane for renting a vehicle.
     * @return VBox containing vehicle rent form
     */
    private VBox createRentVehiclePane() {
        ComboBox<String> vehicleComboBox = new ComboBox<>();
        vehicleComboBox.setPrefWidth(300);
        ComboBox<String> customerComboBox = new ComboBox<>();
        customerComboBox.setPrefWidth(300);
        TextField amountField = new TextField();
        amountField.setPromptText("Rental amount");
        Button rentButton = new Button("Rent Vehicle");

        rentButton.setOnAction(e -> {
            try {
                String selectedVehicleInfo = vehicleComboBox.getValue();
                String selectedCustomerInfo = customerComboBox.getValue();

                if (selectedVehicleInfo == null || selectedCustomerInfo == null) {
                    showAlert("Please select both a vehicle and a customer.");
                    return;
                }

                String plate = selectedVehicleInfo.split("\\|")[1].trim();
                String customerName = selectedCustomerInfo.split(":")[2].trim();

                Vehicle vehicle = rentalSystem.findVehicleByPlate(plate);
                Customer customer = rentalSystem.findCustomerByName(customerName);
                double amount = Double.parseDouble(amountField.getText());

                if (vehicle != null && customer != null) {
                    rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), amount);
                    updateAllLists();
                    showAlert("Vehicle rented successfully.");

                    vehicleComboBox.setValue(null);
                    customerComboBox.setValue(null);
                    amountField.clear();
                } else {
                    showAlert("Vehicle or customer not found.");
                }
            } catch (Exception ex) {
                showAlert("Error renting vehicle: " + ex.getMessage());
            }
        });

        vehicleComboBox.setOnMouseClicked(e -> updateVehicleComboBox(vehicleComboBox));
        customerComboBox.setOnMouseClicked(e -> updateCustomerComboBox(customerComboBox));

        return new VBox(5, new Label("Select Vehicle"), vehicleComboBox, new Label("Select Customer"), customerComboBox, new Label("Amount"), amountField, rentButton);
    }

    /**
     * Creates the pane for returning a vehicle.
     * @return VBox containing vehicle return form
     */
    private VBox createReturnVehiclePane() {
        ComboBox<String> vehicleComboBox = new ComboBox<>();
        vehicleComboBox.setPrefWidth(300);
        ComboBox<String> customerComboBox = new ComboBox<>();
        customerComboBox.setPrefWidth(300);
        TextField feesField = new TextField();
        feesField.setPromptText("Return fees");
        Button returnButton = new Button("Return Vehicle");

        returnButton.setOnAction(e -> {
            try {
                String selectedVehicleInfo = vehicleComboBox.getValue();
                String selectedCustomerInfo = customerComboBox.getValue();

                if (selectedVehicleInfo == null || selectedCustomerInfo == null) {
                    showAlert("Please select both a vehicle and a customer.");
                    return;
                }

                String plate = selectedVehicleInfo.split("\\|")[1].trim();
                String customerName = selectedCustomerInfo.split(":")[2].trim();

                Vehicle vehicle = rentalSystem.findVehicleByPlate(plate);
                Customer customer = rentalSystem.findCustomerByName(customerName);
                double fees = Double.parseDouble(feesField.getText());

                if (vehicle != null && customer != null) {
                    rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), fees);
                    updateAllLists();
                    showAlert("Vehicle returned successfully.");

                    vehicleComboBox.setValue(null);
                    customerComboBox.setValue(null);
                    feesField.clear();
                } else {
                    showAlert("Vehicle or customer not found.");
                }
            } catch (Exception ex) {
                showAlert("Error returning vehicle: " + ex.getMessage());
            }
        });

        vehicleComboBox.setOnMouseClicked(e -> updateVehicleComboBox(vehicleComboBox));
        customerComboBox.setOnMouseClicked(e -> updateCustomerComboBox(customerComboBox));

        return new VBox(5, new Label("Select Vehicle"), vehicleComboBox, new Label("Select Customer"), customerComboBox, new Label("Fees"), feesField, returnButton);
    }

    /**
     * Creates the pane for displaying available vehicles, customers, and rental history.
     * @return VBox containing the display lists
     */
    private VBox createDisplayPane() {
        Button refreshButton = new Button("Refresh Lists");
        refreshButton.setDisable(true); // Auto-refresh is enabled, so manual refresh is disabled

        // Display available vehicles, customers, and rental history
        return new VBox(5, refreshButton,
                new Label("Available Vehicles"), vehicleList,
                new Label("Customers"), customerList,
                new Label("Rental History"), rentalHistoryList);
    }


    /**
     * Updates the vehicle ComboBox with current available vehicles.
     * @param vehicleComboBox ComboBox to update
     */
    private void updateVehicleComboBox(ComboBox<String> vehicleComboBox) {
        vehicleComboBox.getItems().clear();
        for (Vehicle v : rentalSystem.getVehicles()) {
            vehicleComboBox.getItems().add(v.getInfo());
        }
    }

    /**
     * Updates the customer ComboBox with current customers.
     * @param customerComboBox ComboBox to update
     */
    private void updateCustomerComboBox(ComboBox<String> customerComboBox) {
        customerComboBox.getItems().clear();
        for (Customer c : rentalSystem.getCustomers()) {
            customerComboBox.getItems().add(c.toString());
        }
    }


    /**
     * Creates a pane with an Exit button to close the application.
     * @param primaryStage The main window
     * @return VBox containing the exit button
     */
    private VBox createExitPane(Stage primaryStage) {
        Button exitButton = new Button("Exit Application");
        exitButton.setOnAction(e -> primaryStage.close());
        return new VBox(5, new Label("Click below to exit:"), exitButton);
    }

    /**
     * Updates all ListViews: vehicles, customers, and rental history.
     */
    private void updateAllLists() {
        updateVehicleList();
        updateCustomerList();
        updateRentalHistoryList();
    }

    /**
     * Updates the list of vehicles displayed.
     */
    private void updateVehicleList() {
        vehicleList.getItems().clear();
        for (Vehicle v : rentalSystem.getVehicles()) {
            vehicleList.getItems().add(v.getInfo());
        }
    }

    /**
     * Updates the list of customers displayed.
     */
    private void updateCustomerList() {
        customerList.getItems().clear();
        for (Customer c : rentalSystem.getCustomers()) {
            customerList.getItems().add(c.toString());
        }
    }

    /**
     * Updates the rental history list.
     */
    private void updateRentalHistoryList() {
        rentalHistoryList.getItems().clear();
        for (RentalRecord r : rentalSystem.getRentalHistory().getRecords()) {
            rentalHistoryList.getItems().add(r.toString());
        }
    }

    /**
     * Displays an informational alert with the given message.
     * @param message The message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    /**
     * Launches the JavaFX application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
