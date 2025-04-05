package test;

import org.junit.jupiter.api.Test;

import src.*;
import java.time.LocalDate;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Vehicle rental test.
 */
public class VehicleRentalTest {

    /**
     * Test license plate validation.
     */
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
    
    /**
     * Test rent and return vehicle.
     */
    @Test
    void testRentAndReturnVehicle() {
        // Rent
        RentalSystem rentalSystem = RentalSystem.getInstance();
        Vehicle testVehicle = new Car("Toyota", "Camry", 2020, 5);
        Customer testCustomer = new Customer(001, "George");
        
        assertEquals(Vehicle.VehicleStatus.AVAILABLE, testVehicle.getStatus());
        assertTrue(rentalSystem.rentVehicle(testVehicle, testCustomer, LocalDate.now(), 500));
        assertEquals(Vehicle.VehicleStatus.RENTED, testVehicle.getStatus());
        
        assertFalse(rentalSystem.rentVehicle(testVehicle, testCustomer, LocalDate.now(), 500));
        
        // Return
        assertTrue(rentalSystem.returnVehicle(testVehicle, testCustomer, LocalDate.now(), 20));
        assertEquals(Vehicle.VehicleStatus.AVAILABLE, testVehicle.getStatus());
        
        assertFalse(rentalSystem.returnVehicle(testVehicle, testCustomer, LocalDate.now(), 20));
    }

    /**
     * Test singleton rental system.
     *
     * @throws Exception the exception
     */
    @Test
    void testSingletonRentalSystem() throws Exception{
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
        
        assertEquals(Modifier.PRIVATE, constructor.getModifiers());
        
        assertNotNull(RentalSystem.getInstance());
        
    }
}
