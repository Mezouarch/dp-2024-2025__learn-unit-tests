package fr.anthonyquere.teashop;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeaShopTest {
    
    private TeaShop teaShop;
    private Tea greenTea;
    
    @BeforeEach
    public void setup() {
        teaShop = new TeaShop(90);
        greenTea = new Tea("Green Tea", 120, 80, true);
    }
    
    @Test
    public void testAddAndPrepareTea() {
        // Act
        teaShop.addTea(greenTea);
        TeaCup cup = teaShop.prepareTea("Green Tea");
        
        // Assert
        assertNotNull(cup);
        // Cup has been prepared successfully if no exception is thrown
    }
    
    @Test
    public void testAddTeaIsCaseInsensitive() {
        // Arrange
        teaShop.addTea(greenTea);
        
        // Act & Assert - Should be able to find tea with different case
        TeaCup cup = teaShop.prepareTea("green tea");
        assertNotNull(cup);
    }
    
    @Test
    public void testPrepareUnavailableTea() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            teaShop.prepareTea("Earl Grey");
        });
        
        assertEquals("Tea not available: Earl Grey", exception.getMessage());
    }
    
    @Test
    public void testSetWaterTemperatureInvalid() {
        // Act & Assert - Temperature too low
        Exception lowException = assertThrows(IllegalArgumentException.class, () -> {
            teaShop.setWaterTemperature(-5);
        });
        
        assertEquals("Water temperature must be between 0 and 100°C", lowException.getMessage());
        
        // Act & Assert - Temperature too high
        Exception highException = assertThrows(IllegalArgumentException.class, () -> {
            teaShop.setWaterTemperature(105);
        });
        
        assertEquals("Water temperature must be between 0 and 100°C", highException.getMessage());
    }
    
    @Test
    public void testSetWaterTemperatureValid() {
        // Act
        teaShop.setWaterTemperature(85);
        
        // Test indirectly by preparing tea with the expected temperature
        // First add a tea
        teaShop.addTea(greenTea);
        
        // Then prepare the tea - if water temperature was set correctly, this should work
        teaShop.prepareTea("Green Tea");
        
        // Test passes if no exception is thrown
    }
    
    @Test
    public void testStartTeaShop() {
        // Act
        List<String> operations = TeaShop.startTeaShop();
        
        // Assert
        assertNotNull(operations);
        assertTrue(!operations.isEmpty());
        assertEquals("=== Tea Shop Simulation ===", operations.get(0));
        
        // Check key operations
        assertTrue(operations.contains("Tea shop initialized with water temperature: 90°C"));
        assertTrue(operations.contains("Added tea: Green Tea"));
        assertTrue(operations.contains("Added tea: Earl Grey"));
        assertTrue(operations.contains("Added tea: Chamomile"));
        assertTrue(operations.contains("Water temperature set to: 80°C"));
        assertTrue(operations.contains("Water temperature set to: 95°C"));
        assertTrue(operations.contains("Successfully prepared 'chamomile' (lowercase) tea"));
        assertTrue(operations.contains("Error: Tea not available: Matcha"));
        assertTrue(operations.contains("Error: Water temperature must be between 0 and 100°C"));
        assertTrue(operations.contains("=== End of Tea Shop Simulation ==="));
    }
    
    @Test
    public void testGetWaterTemperature() {
        // Act & Assert
        assertEquals(90, teaShop.getWaterTemperature());
        
        // Change and check again
        teaShop.setWaterTemperature(75);
        assertEquals(75, teaShop.getWaterTemperature());
    }
    
    @Test
    public void testGetAvailableTeas() {
        // Arrange
        teaShop.addTea(greenTea);
        Tea earlGrey = new Tea("Earl Grey", 180, 95, false);
        teaShop.addTea(earlGrey);
        
        // Act
        var teas = teaShop.getAvailableTeas();
        
        // Assert
        assertEquals(2, teas.size());
        assertTrue(teas.containsKey("green tea"));
        assertTrue(teas.containsKey("earl grey"));
    }
}