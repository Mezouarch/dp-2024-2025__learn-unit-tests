package fr.anthonyquere.teashop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeaShop {
    private final Map<String, Tea> availableTeas = new HashMap<>();
    private int waterTemperature;

    public TeaShop(int defaultWaterTemperature) {
        this.waterTemperature = defaultWaterTemperature;
    }

    public void addTea(Tea tea) {
        availableTeas.put(tea.getName().toLowerCase(), tea);
    }

    public TeaCup prepareTea(String teaName) {
        Tea tea = availableTeas.get(teaName.toLowerCase());
        if (tea == null) {
            throw new IllegalArgumentException("Tea not available: " + teaName);
        }

        TeaCup cup = new TeaCup();
        cup.addWater(waterTemperature);
        cup.addTea(tea);
        return cup;
    }

    public void setWaterTemperature(int celsius) {
        if (celsius < 0 || celsius > 100) {
            throw new IllegalArgumentException("Water temperature must be between 0 and 100°C");
        }
        this.waterTemperature = celsius;
    }

    public int getWaterTemperature() {
        return waterTemperature;
    }

    public Map<String, Tea> getAvailableTeas() {
        return availableTeas;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        startTeaShop().forEach(System.out::println);
    }

    /**
     * Simulate running a tea shop with various operations
     * @return List of operations performed in the tea shop
     */
    public static List<String> startTeaShop() {
        List<String> operations = new ArrayList<>();
        operations.add("=== Tea Shop Simulation ===");
        
        // Initialize tea shop
        TeaShop shop = new TeaShop(90);
        operations.add("Tea shop initialized with water temperature: " + shop.getWaterTemperature() + "°C");
        
        // Create different teas
        Tea greenTea = new Tea("Green Tea", 120, 80, true);
        Tea earlGrey = new Tea("Earl Grey", 180, 95, false);
        Tea chamomile = new Tea("Chamomile", 300, 100, true);
        
        // Add teas to the shop
        shop.addTea(greenTea);
        operations.add("Added tea: " + greenTea.getName());
        shop.addTea(earlGrey);
        operations.add("Added tea: " + earlGrey.getName());
        shop.addTea(chamomile);
        operations.add("Added tea: " + chamomile.getName());
        
        // Display available teas
        operations.add("\nAvailable teas:");
        for (String teaName : shop.getAvailableTeas().keySet()) {
            operations.add("- " + teaName);
        }
        
        // Prepare some teas
        operations.add("\nPreparing teas:");
        try {
            // Prepare green tea
            shop.prepareTea("Green Tea");
            operations.add("Prepared Green Tea at " + shop.getWaterTemperature() + "°C");
            
            // Adjust water temperature for optimal green tea brewing
            operations.add("\nAdjusting water temperature for optimal green tea brewing...");
            shop.setWaterTemperature(80);
            operations.add("Water temperature set to: " + shop.getWaterTemperature() + "°C");
            
            // Prepare another green tea with optimal temperature
            shop.prepareTea("Green Tea");
            operations.add("Prepared Green Tea at optimal temperature: " + shop.getWaterTemperature() + "°C");
            
            // Adjust water temperature for black tea
            operations.add("\nAdjusting water temperature for black tea...");
            shop.setWaterTemperature(95);
            operations.add("Water temperature set to: " + shop.getWaterTemperature() + "°C");
            
            // Prepare Earl Grey
            shop.prepareTea("Earl Grey");
            operations.add("Prepared Earl Grey at optimal temperature: " + shop.getWaterTemperature() + "°C");
            
            // Try case-insensitive tea name
            operations.add("\nTesting case insensitivity:");
            shop.prepareTea("chamomile");
            operations.add("Successfully prepared 'chamomile' (lowercase) tea");
            
            // Try unavailable tea (will throw exception, we'll catch it)
            operations.add("\nTrying to prepare unavailable tea:");
            try {
                shop.prepareTea("Matcha");
            } catch (IllegalArgumentException e) {
                operations.add("Error: " + e.getMessage());
            }
            
            // Try invalid temperature (will throw exception, we'll catch it)
            operations.add("\nTrying to set invalid temperature:");
            try {
                shop.setWaterTemperature(105);
            } catch (IllegalArgumentException e) {
                operations.add("Error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            operations.add("Unexpected error: " + e.getMessage());
        }
        
        operations.add("\n=== End of Tea Shop Simulation ===");
        return operations;
    }
}