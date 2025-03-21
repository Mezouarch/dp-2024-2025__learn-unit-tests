package fr.anthonyquere.teashop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TeaTest {
    
    @Test
    public void testConstructorAndGetters() {
        // Arrange
        String name = "Green Tea";
        int steepingTime = 120;
        int idealTemp = 80;
        boolean isLoose = true;
        
        // Act
        Tea tea = new Tea(name, steepingTime, idealTemp, isLoose);
        
        // Assert
        assertEquals(name, tea.getName());
        assertEquals(steepingTime, tea.getSteepingTimeSeconds());
        assertEquals(idealTemp, tea.getIdealTemperatureCelsius());
        assertTrue(tea.isLoose());
    }
    
    @Test
    public void testSetters() {
        // Arrange
        Tea tea = new Tea("Initial", 60, 70, false);
        
        // Act
        tea.setName("Earl Grey");
        tea.setSteepingTimeSeconds(180);
        tea.setIdealTemperatureCelsius(90);
        tea.setLoose(true);
        
        // Assert
        assertEquals("Earl Grey", tea.getName());
        assertEquals(180, tea.getSteepingTimeSeconds());
        assertEquals(90, tea.getIdealTemperatureCelsius());
        assertTrue(tea.isLoose());
    }
    
    @Test
    public void testStartTeaMethod() {
        // Act
        List<String> steps = Tea.startTea(2);
        
        // Assert
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        assertEquals("Starting tea preparation for 3 tea(s)", steps.get(0));
        assertTrue(steps.contains("Boiling water..."));
        assertTrue(steps.contains("Tea preparation completed!"));
    }
    
    @Test
    public void testStartTeaWithParameter() {
        // Act
        List<String> steps = Tea.startTea(2);
        
        // Assert
        assertNotNull(steps);
        assertEquals("Starting tea preparation for 2 tea(s)", steps.get(0));
        
        // Verify steps for Green Tea
        assertTrue(steps.contains("Preparing Green Tea"));
        assertTrue(steps.contains("Cooling water to 80°C"));
        assertTrue(steps.contains("Placing loose tea leaves in an infuser"));
        assertTrue(steps.contains("Steeping for 120 seconds"));
        
        // Verify steps for Earl Grey
        assertTrue(steps.contains("Preparing Earl Grey"));
        assertTrue(steps.contains("Cooling water to 95°C"));
        assertTrue(steps.contains("Placing tea bag in cup"));
        assertTrue(steps.contains("Steeping for 180 seconds"));
        
        // Verify that no steps for Chamomile exist
        assertFalse(steps.contains("Preparing Chamomile"));
    }
    
    @Test
    public void testStartTeaModifiesProperties() {
        // Act
        List<String> steps = Tea.startTea(1);
        
        // Assert
        assertNotNull(steps);
        assertTrue(steps.contains("Tea updated: Green Tea (Prepared)"));
        assertTrue(steps.contains("New steeping time: 150 seconds"));
        assertTrue(steps.contains("New ideal temperature: 75°C"));
        assertTrue(steps.contains("Is now tea bag"));
    }
}