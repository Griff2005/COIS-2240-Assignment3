package test;

import org.junit.jupiter.api.Test;
import src.Car;
import src.*;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleRentalTest {
    
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
}
