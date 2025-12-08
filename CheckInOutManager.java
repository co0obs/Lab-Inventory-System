import java.util.ArrayList;
import java.util.Optional;

public class CheckInOutManager {
    
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ItemManager itemManager;
    
    public CheckInOutManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    // Method for saving data
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // Method for loading data
    public void loadTransaction(Transaction t) {
        transactions.add(t);
    }
    
    // Check out
    public boolean checkOut(String userId, String itemName, int quantity, boolean isLabTech) {
        Optional<Item> itemOpt = itemManager.findItemByName(itemName);
        
        if (!itemOpt.isPresent()) {
            System.out.println("Error: Item not found.");
            return false;
        }
        
        Item item = itemOpt.get();
        
        if (!isLabTech && item.getAccessLevel() == AccessLevel.LAB_TECH_ONLY) {
            System.out.println("Error: Only Lab Technicians can access this item.");
            return false;
        }
        
        if (item.getQuantity() < quantity) {
            System.out.println("Error: Only " + item.getQuantity() + " available.");
            return false;
        }
        
        if (itemManager.decreaseQuantity(itemName, quantity)) {
            Transaction t = new Transaction(userId, itemName, quantity);
            transactions.add(t);
            System.out.println("Checked out successfully!");
            return true;
        }
        
        return false;
    }
    
    // Check in
    public boolean checkIn(String userId, String itemName, int quantity) {
        Transaction active = null;
        
        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId) && 
                t.getItemName().equalsIgnoreCase(itemName) && 
                !t.isReturned()) {
                active = t;
                break;
            }
        }
        
        if (active == null) {
            System.out.println("Error: No active checkout found.");
            return false;
        }
        
        if (quantity > active.getQuantity()) {
            System.out.println("Error: You only borrowed " + active.getQuantity());
            return false;
        }
        
        active.markAsReturned();
        itemManager.increaseQuantity(itemName, quantity);
        
        System.out.println("Checked in successfully!");
        return true;
    }
    
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
            System.out.println("None.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        if (transactions.isEmpty()) {
            System.out.println("No transaction history.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

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
}