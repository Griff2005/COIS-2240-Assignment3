package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Car;
import src.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleRentalTest {

    private RentalSystem rentalSystem;

    @BeforeEach
    void setUp() {
        this.rentalSystem = RentalSystem.getInstance();
    }
    
    @Test
    void testLicensePlateValidation() {
        Vehicle testVehicle = new Car("Toyota", "Camry", 2020, 5);
        
        assertDoesNotThrow(() -> testVehicle.setLicensePlate("AAA100"));
        assertEquals("AAA100", testVehicle.getLicensePlate());

        assertDoesNotThrow(() -> testVehicle.setLicensePlate("ABC567"));
        assertEquals("ABC567", testVehicle.getLicensePlate());

        assertDoesNotThrow(() -> testVehicle.setLicensePlate("ZZZ999"));
        assertEquals("ZZZ999", testVehicle.getLicensePlate());
        
        assertThrows(IllegalArgumentException.class, () -> testVehicle.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> testVehicle.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> testVehicle.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> testVehicle.setLicensePlate("ZZZ99"));
    }
    
    @Test
    void testRentAndReturnVehicle() {
        Vehicle testVehicle = new Car("Toyota", "Camry", 2020, 5);
        Customer testCustomer = new Customer(001, "George");
        
        assertEquals(Vehicle.VehicleStatus.AVAILABLE, testVehicle.getStatus());
        
        assertTrue(rentalSystem.rentVehicle(testVehicle, testCustomer, LocalDate.now(), 500));
        assertEquals(Vehicle.VehicleStatus.RENTED, testVehicle.getStatus());
        
        assertFalse(rentalSystem.rentVehicle(testVehicle, testCustomer, LocalDate.now(), 500));
    }
}
