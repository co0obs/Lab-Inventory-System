import java.util.ArrayList;
import java.util.Optional;

public class CheckInOutManager {
    
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ItemManager itemManager;
    
    // Constructor
    public CheckInOutManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
    
    // Check out an item
    public boolean checkOut(String userId, String itemName, int quantity, boolean isLabTech) {
        // Find the item
        Optional<Item> itemOpt = itemManager.findItemByName(itemName);
        
        if (!itemOpt.isPresent()) {
            System.out.println("Error: Item not found in inventory.");
            return false;
        }
        
        Item item = itemOpt.get();
        
        // Check access permission
        if (!isLabTech && item.getAccessLevel() == AccessLevel.LAB_TECH_ONLY) {
            System.out.println("Error: This item requires Lab Technician access.");
            return false;
        }
        
        // Check availability
        if (item.getQuantity() < quantity) {
            System.out.println("Error: Not enough quantity available. Current stock: " + item.getQuantity());
            return false;
        }
        
        // Decrease quantity
        if (itemManager.decreaseQuantity(itemName, quantity)) {
            // Create transaction record
            Transaction transaction = new Transaction(userId, itemName, quantity);
            transactions.add(transaction);
            System.out.println("Success! " + quantity + "x " + itemName + " checked out to " + userId);
            return true;
        }
        
        return false;
    }
    
    // Check in an item
    public boolean checkIn(String userId, String itemName, int quantity) {
        // Find active transaction for this user and item
        Transaction activeTransaction = null;
        
        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId) && 
                t.getItemName().equalsIgnoreCase(itemName) && 
                !t.isReturned()) {
                activeTransaction = t;
                break;
            }
        }
        
        if (activeTransaction == null) {
            System.out.println("Error: No active checkout found for this item under your ID.");
            return false;
        }
        
        if (quantity > activeTransaction.getQuantity()) {
            System.out.println("Error: You only checked out " + activeTransaction.getQuantity() + " of this item.");
            return false;
        }
        
        // Mark as returned
        activeTransaction.markAsReturned();
        
        // Increase inventory quantity
        itemManager.increaseQuantity(itemName, quantity);
        
        System.out.println("Success! " + quantity + "x " + itemName + " returned by " + userId);
        return true;
    }
    
    // View borrowed items for a specific user
    public void viewUserBorrowedItems(String userId) {
        System.out.println("\n--- Your Borrowed Items ---");
        boolean found = false;
        
        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId) && !t.isReturned()) {
                System.out.println(t);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("You have no items currently borrowed.");
        }
    }
    
    // View all transactions (Lab Tech only)
    public void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                System.out.println((i + 1) + ". " + transactions.get(i));
            }
        }
    }
    
    // View all active checkouts (Lab Tech only)
    public void viewActiveCheckouts() {
        System.out.println("\n--- Active Checkouts ---");
        boolean found = false;
        
        for (Transaction t : transactions) {
            if (!t.isReturned()) {
                System.out.println(t);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No active checkouts.");
        }
    }
    
    // View transaction history (Lab Tech only)
    public void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        
        if (transactions.isEmpty()) {
            System.out.println("No transaction history.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }
}