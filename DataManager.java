import java.io.*;
import java.util.ArrayList;

public class DataManager {
    
    private static final String INVENTORY_FILE = "inventory.dat";
    
    // Save inventory to file
    public static void saveInventory(ArrayList<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Item item : items) {
                String line = item.getName() + "," + 
                             item.getQuantity() + "," + 
                             item.getCategory() + "," + 
                             item.getAccessLevel();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("\nInventory saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
    
    // Load inventory from file
    public static void loadInventory(ItemManager itemManager) {
        File file = new File(INVENTORY_FILE);
        
        if (!file.exists()) {
            System.out.println("No saved inventory found. Starting fresh.\n");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                
                if (parts.length == 4) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    String category = parts[2];
                    AccessLevel accessLevel = AccessLevel.valueOf(parts[3]);
                    
                    // Add item without duplicate check
                    Item newItem = new Item(name, quantity, category, accessLevel);
                    itemManager.getItems().add(newItem);
                    count++;
                }
            }
            
            if (count > 0) {
                System.out.println("Loaded " + count + " items from saved inventory.\n");
            }
            
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing inventory data: " + e.getMessage());
        }
    }
}