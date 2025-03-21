package fr.anthonyquere.teashop;

import java.util.ArrayList;
import java.util.List;

public class TeaCup {
    private int currentTemperatureCelsius;
    private Tea tea;
    private boolean isEmpty = true;
    private int steepingStartTime; // in seconds

    public void addWater(int temperatureCelsius) {
        this.currentTemperatureCelsius = temperatureCelsius;
        this.isEmpty = false;
    }

    public void addTea(Tea tea) {
        if (isEmpty) {
            throw new IllegalStateException("Cannot add tea to an empty cup!");
        }
        this.tea = tea;
        this.steepingStartTime = getCurrentTimeInSeconds();
    }

    public boolean isReadyToDrink() {
        if (tea == null || isEmpty) return false;

        int steepingTime = getCurrentTimeInSeconds() - steepingStartTime;
        return steepingTime >= tea.getSteepingTimeSeconds() &&
                isTemperatureIdeal();
    }

    protected boolean isTemperatureIdeal() {
        return Math.abs(currentTemperatureCelsius - tea.getIdealTemperatureCelsius()) <= 5;
    }

    protected int getCurrentTimeInSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }
    
    public int getCurrentTemperatureCelsius() {
        return currentTemperatureCelsius;
    }
    
    public Tea getTea() {
        return tea;
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }
    
    public int getSteepingStartTime() {
        return steepingStartTime;
    }
    
    public int getSteepingTimeElapsed() {
        if (tea == null || isEmpty) return 0;
        return getCurrentTimeInSeconds() - steepingStartTime;
    }
    
    public int getSteepingTimeRemaining() {
        if (tea == null || isEmpty) return 0;
        int elapsed = getSteepingTimeElapsed();
        int required = tea.getSteepingTimeSeconds();
        return Math.max(0, required - elapsed);
    }
    
    
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        startTeaCup().forEach(System.out::println);
    }
    /**
     * Simulate the tea cup preparation and steeping process
     * @return List of steps in the tea cup preparation process
     */
    public static List<String> startTeaCup() {
        List<String> steps = new ArrayList<>();
        steps.add("=== Tea Cup Preparation Simulation ===");
        
        // Create a testable cup that we can manipulate time on
        TestableTeaCup cup = new TestableTeaCup();
        steps.add("Created a new tea cup");
        
        // Create a tea
        Tea greenTea = new Tea("Green Tea", 120, 80, true);
        steps.add("Created " + greenTea.getName() + " (steeping time: " + 
                  greenTea.getSteepingTimeSeconds() + "s, ideal temp: " + 
                  greenTea.getIdealTemperatureCelsius() + "°C)");
        
        // Check if cup is empty
        steps.add("\nCup isEmpty: " + cup.isEmpty());
        
        // Try to add tea to empty cup (should fail)
        steps.add("\nAttempting to add tea to empty cup...");
        try {
            cup.addTea(greenTea);
            steps.add("ERROR: This should have thrown an exception!");
        } catch (IllegalStateException e) {
            steps.add("Correctly got exception: " + e.getMessage());
        }
        
        // Add water to cup
        int waterTemp = 85;
        steps.add("\nAdding water at " + waterTemp + "°C");
        cup.addWater(waterTemp);
        steps.add("Cup isEmpty: " + cup.isEmpty());
        steps.add("Cup temperature: " + cup.getCurrentTemperatureCelsius() + "°C");
        
        // Check if tea is ready (should be false, no tea added yet)
        steps.add("\nChecking if tea is ready to drink...");
        steps.add("Is ready to drink: " + cup.isReadyToDrink());
        
        // Add tea to cup
        steps.add("\nAdding " + greenTea.getName() + " to cup");
        cup.setCurrentTime(1000); // Set initial time
        cup.addTea(greenTea);
        steps.add("Tea added at time: " + cup.getSteepingStartTime() + " seconds");
        
        // Check if tea is ready right after adding (should be false, not steeped long enough)
        steps.add("\nChecking if tea is ready right after adding...");
        steps.add("Is ready to drink: " + cup.isReadyToDrink());
        steps.add("Steeping time elapsed: " + cup.getSteepingTimeElapsed() + " seconds");
        steps.add("Steeping time remaining: " + cup.getSteepingTimeRemaining() + " seconds");
        
        // Simulate time passing, but not enough
        cup.setCurrentTime(1060); // 60 seconds later
        steps.add("\nTime passed: 60 seconds");
        steps.add("Is ready to drink: " + cup.isReadyToDrink());
        steps.add("Steeping time elapsed: " + cup.getSteepingTimeElapsed() + " seconds");
        steps.add("Steeping time remaining: " + cup.getSteepingTimeRemaining() + " seconds");
        
        // Simulate time passing, enough time but wrong temperature
        cup.setCurrentTime(1130); // 130 seconds later (> 120 required)
        cup.setIdealTemperatureOffset(10); // Set temperature to be non-ideal
        steps.add("\nTime passed: 130 seconds (steeping complete)");
        steps.add("But temperature is not ideal (offset: 10°C)");
        steps.add("Is ready to drink: " + cup.isReadyToDrink());
        steps.add("Steeping time elapsed: " + cup.getSteepingTimeElapsed() + " seconds");
        steps.add("Steeping time remaining: " + cup.getSteepingTimeRemaining() + " seconds");
        
        // Simulate perfect conditions
        cup.setCurrentTime(1150); // 150 seconds later
        cup.setIdealTemperatureOffset(0); // Set temperature to be ideal
        steps.add("\nTime passed: 150 seconds, temperature is now ideal");
        steps.add("Is ready to drink: " + cup.isReadyToDrink());
        steps.add("Steeping time elapsed: " + cup.getSteepingTimeElapsed() + " seconds");
        steps.add("Steeping time remaining: " + cup.getSteepingTimeRemaining() + " seconds");
        
        steps.add("\n=== End of Tea Cup Simulation ===");
        return steps;
    }
    
    // This subclass allows us to control time for testing purposes
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
}