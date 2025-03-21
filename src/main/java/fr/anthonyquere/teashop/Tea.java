package fr.anthonyquere.teashop;

import java.util.ArrayList;
import java.util.List;

public class Tea {
    private String name;
    private int steepingTimeSeconds;
    private int idealTemperatureCelsius;
    private boolean isLoose; // loose leaf vs tea bag

    public Tea(String name, int steepingTimeSeconds, int idealTemperatureCelsius, boolean isLoose) {
        this.name = name;
        this.steepingTimeSeconds = steepingTimeSeconds;
        this.idealTemperatureCelsius = idealTemperatureCelsius;
        this.isLoose = isLoose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSteepingTimeSeconds() {
        return steepingTimeSeconds;
    }

    public void setSteepingTimeSeconds(int steepingTimeSeconds) {
        this.steepingTimeSeconds = steepingTimeSeconds;
    }

    public int getIdealTemperatureCelsius() {
        return idealTemperatureCelsius;
    }

    public void setIdealTemperatureCelsius(int idealTemperatureCelsius) {
        this.idealTemperatureCelsius = idealTemperatureCelsius;
    }

    public boolean isLoose() {
        return isLoose;
    }

    public void setLoose(boolean loose) {
        isLoose = loose;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        startTea(1).forEach(System.out::println);
    }

    public static List<String> startTea() {
        return startTea(3); // Default preparation for 3 teas
    }

    public static List<String> startTea(int numberOfTeas) {
        List<String> steps = new ArrayList<>();
        
        // Create example teas
        Tea[] teas = new Tea[numberOfTeas];
        
        // Initialize teas with different properties
        teas[0] = new Tea("Green Tea", 120, 80, true);
        
        if (numberOfTeas > 1) {
            teas[1] = new Tea("Earl Grey", 180, 95, false);
        }
        
        if (numberOfTeas > 2) {
            teas[2] = new Tea("Chamomile", 300, 100, true);
        }
        
        // Add general preparation steps
        steps.add("Starting tea preparation for " + numberOfTeas + " tea(s)");
        steps.add("Boiling water...");
        
        // Add specific steps for each tea
        for (int i = 0; i < numberOfTeas; i++) {
            Tea tea = teas[i];
            steps.add("----------------");
            steps.add("Preparing " + tea.getName());
            
            // Adjust water temperature
            steps.add("Cooling water to " + tea.getIdealTemperatureCelsius() + "°C");
            
            // Prepare tea based on type
            if (tea.isLoose()) {
                steps.add("Placing loose tea leaves in an infuser");
            } else {
                steps.add("Placing tea bag in cup");
            }
            
            // Steep tea
            steps.add("Steeping for " + tea.getSteepingTimeSeconds() + " seconds");
            
            // Remove tea
            if (tea.isLoose()) {
                steps.add("Removing infuser");
            } else {
                steps.add("Removing tea bag");
            }
            
            // Modify tea properties to demonstrate setters
            String originalName = tea.getName();
            tea.setName(originalName + " (Prepared)");
            tea.setSteepingTimeSeconds(tea.getSteepingTimeSeconds() + 30);
            tea.setLoose(!tea.isLoose());
            tea.setIdealTemperatureCelsius(tea.getIdealTemperatureCelsius() - 5);
            
            // Show updated tea properties
            steps.add("Tea updated: " + tea.getName());
            steps.add("New steeping time: " + tea.getSteepingTimeSeconds() + " seconds");
            steps.add("New ideal temperature: " + tea.getIdealTemperatureCelsius() + "°C");
            steps.add("Is now " + (tea.isLoose() ? "loose leaf" : "tea bag"));
        }
        
        steps.add("----------------");
        steps.add("Tea preparation completed!");
        
        return steps;
    }
}