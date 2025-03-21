package fr.anthonyquere.teashop;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeaCupTest {
    
    private TeaCup teaCup;
    private Tea simpleTea;
    
    @BeforeEach
    public void setup() {
        teaCup = new TeaCup();
        simpleTea = new Tea("Simple Tea", 120, 80, false);
    }
    
    @Test
    public void testAddWater() {
        // Act
        teaCup.addWater(85);
        
        // Assert - indirectly test by checking if we can add tea without exception
        teaCup.addTea(simpleTea);
        // Test passes if no exception is thrown
    }
    
    @Test
    public void testAddTeaToEmptyCup() {
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            teaCup.addTea(simpleTea);
        });
        
        assertEquals("Cannot add tea to an empty cup!", exception.getMessage());
    }
    
    @Test
    public void testAddTeaToFilledCup() {
        // Arrange
        teaCup.addWater(90);
        
        // Act
        teaCup.addTea(simpleTea);
        
        // Assert - indirect: check isReadyToDrink doesn't return false due to null tea
        // We can't assert on the result because it depends on time, but at least
        // we know the method runs without error
        teaCup.isReadyToDrink();
    }
    
    @Test
    public void testIsReadyToDrinkWithEmptyCup() {
        // Act & Assert
        assertFalse(teaCup.isReadyToDrink());
    }
    
    // This subclass overrides getCurrentTimeInSeconds to make it testable
    private static class TestableTeaCup extends TeaCup {
        private int currentTimeSeconds = 1000;
        private int idealTemperatureOffset = 0;
        
        public void setCurrentTime(int timeInSeconds) {
            this.currentTimeSeconds = timeInSeconds;
        }
        
        public void setIdealTemperatureOffset(int offset) {
            this.idealTemperatureOffset = offset;
        }
        
        @Override
        protected int getCurrentTimeInSeconds() {
            return currentTimeSeconds;
        }
        
        @Override
        protected boolean isTemperatureIdeal() {
            if (getTea() == null) return false;
            // If offset is 0, return true (ideal temperature)
            // If offset is not 0, return false (not ideal temperature)
            return idealTemperatureOffset == 0;
        }
    }
    
    @Test
    public void testIsReadyToDrinkBasedOnTime() {
        // Arrange
        TestableTeaCup testCup = new TestableTeaCup();
        testCup.addWater(80);
        
        Tea tea = new Tea("Test Tea", 120, 80, false);
        testCup.setCurrentTime(1000); // Set initial time
        testCup.addTea(tea);
        
        // Act & Assert - Not steeped long enough
        assertFalse(testCup.isReadyToDrink());
        
        // Act & Assert - Steeped long enough
        testCup.setCurrentTime(1121); // 1000 + 121 seconds
        assertTrue(testCup.isReadyToDrink());
    }
    
    @Test
    public void testIsReadyToDrinkBasedOnTemperature() {
        // Arrange
        TestableTeaCup testCup = new TestableTeaCup();
        testCup.addWater(80);
        
        Tea tea = new Tea("Test Tea", 0, 80, false); // 0 steeping time to isolate temperature test
        testCup.addTea(tea);
        
        // Act & Assert - Ideal temperature
        testCup.setIdealTemperatureOffset(0);
        assertTrue(testCup.isReadyToDrink());
        
        // Act & Assert - Non-ideal temperature
        testCup.setIdealTemperatureOffset(10);
        assertFalse(testCup.isReadyToDrink());
    }
    
    @Test
    public void testStartTeaCup() {
        // Act
        List<String> steps = TeaCup.startTeaCup();
        
        // Assert
        assertNotNull(steps);
        assertTrue(!steps.isEmpty());
        assertEquals("=== Tea Cup Preparation Simulation ===", steps.get(0));
        
        // Check that key steps are included
        assertTrue(steps.contains("Created a new tea cup"));
        assertTrue(steps.contains("Cup isEmpty: true"));
        assertTrue(steps.contains("Correctly got exception: Cannot add tea to an empty cup!"));
        assertTrue(steps.contains("Cup isEmpty: false"));
        assertTrue(steps.contains("Is ready to drink: true"));
        assertTrue(steps.contains("=== End of Tea Cup Simulation ==="));
    }
    
    @Test
    public void testGetCurrentTemperatureCelsius() {
        // Arrange
        teaCup.addWater(85);
        
        // Act & Assert
        assertEquals(85, teaCup.getCurrentTemperatureCelsius());
    }
    
    @Test
    public void testGetTea() {
        // Arrange
        teaCup.addWater(85);
        teaCup.addTea(simpleTea);
        
        // Act
        Tea result = teaCup.getTea();
        
        // Assert
        assertEquals(simpleTea, result);
    }
    
    @Test
    public void testIsEmpty() {
        // Act & Assert - Initially empty
        assertTrue(teaCup.isEmpty());
        
        // Act & Assert - After adding water
        teaCup.addWater(85);
        assertFalse(teaCup.isEmpty());
    }
    
    @Test
    public void testGetSteepingTimeElapsed() {
        // Arrange
        TestableTeaCup testCup = new TestableTeaCup();
        testCup.addWater(80);
        
        // Act & Assert - Before adding tea
        assertEquals(0, testCup.getSteepingTimeElapsed());
        
        // Act & Assert - After adding tea
        testCup.setCurrentTime(1000);
        testCup.addTea(simpleTea);
        assertEquals(0, testCup.getSteepingTimeElapsed());
        
        // Act & Assert - After time passes
        testCup.setCurrentTime(1060);
        assertEquals(60, testCup.getSteepingTimeElapsed());
    }
    
    @Test
    public void testGetSteepingTimeRemaining() {
        // Arrange
        TestableTeaCup testCup = new TestableTeaCup();
        testCup.addWater(80);
        
        // Act & Assert - Before adding tea
        assertEquals(0, testCup.getSteepingTimeRemaining());
        
        // Act & Assert - After adding tea
        testCup.setCurrentTime(1000);
        testCup.addTea(simpleTea);
        assertEquals(120, testCup.getSteepingTimeRemaining());
        
        // Act & Assert - After some time passes
        testCup.setCurrentTime(1060);
        assertEquals(60, testCup.getSteepingTimeRemaining());
        
        // Act & Assert - After all time passes
        testCup.setCurrentTime(1130);
        assertEquals(0, testCup.getSteepingTimeRemaining());
    }
}