import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DataManager {
    
    private static final String INVENTORY_FILE = "inventory.dat";
    private static final String TRANSACTION_FILE = "transactions.dat";
    
    // Load method
    public static void loadData(ItemManager itemManager, CheckInOutManager checkInOutManager) {
        loadInventory(itemManager);
        loadTransactions(checkInOutManager);
    }

    // Save Inventory
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
            JOptionPane.showMessageDialog(null, "Inventory saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving inventory: " + e.getMessage());
        }
    }
    
    // Save transactions
    public static void saveTransactions(ArrayList<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Transaction t : transactions) {
                String checkInStr = (t.getCheckInTime() == null) ? "null" : t.getCheckInTime().toString();
                
                String line = t.getUserId() + "," + 
                              t.getItemName() + "," + 
                              t.getQuantity() + "," + 
                              t.isReturned() + "," + 
                              t.getCheckOutTime().toString() + "," + 
                              checkInStr;
                              
                writer.write(line);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Transactions history saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving transactions: " + e.getMessage());
        }
    }

    // Load inventory
    private static void loadInventory(ItemManager itemManager) {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No saved inventory found. Starting with empty stock.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    itemManager.addItem(parts[0], Integer.parseInt(parts[1]), parts[2], AccessLevel.valueOf(parts[3]));
                    count++;
                }
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Loaded " + count + " item(s) from inventory.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading inventory: " + e.getMessage());
        }
    }

    // Load transactions
    private static void loadTransactions(CheckInOutManager manager) {
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) return; 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        String userId = parts[0];
                        String itemName = parts[1];
                        int qty = Integer.parseInt(parts[2]);
                        boolean isReturned = Boolean.parseBoolean(parts[3]);
                        LocalDateTime checkOut = LocalDateTime.parse(parts[4]);
                        LocalDateTime checkIn = parts[5].equals("null") ? null : LocalDateTime.parse(parts[5]);
                        
                        Transaction t = new Transaction(userId, itemName, qty, checkOut, checkIn, isReturned);
                        manager.loadTransaction(t);
                        count++;
                    } catch (Exception parseEx) {
                        System.out.println("Skipping corrupted transaction line.");
                    }
                }
            }
            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Loaded " + count + " past transactions.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading transactions: " + e.getMessage());
        }
    }
}