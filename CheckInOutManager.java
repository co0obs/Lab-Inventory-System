import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(null, "Error: Item not found.");
            return false;
        }
        
        Item item = itemOpt.get();
        
        if (!isLabTech && item.getAccessLevel() == AccessLevel.LAB_TECH_ONLY) {
            JOptionPane.showMessageDialog(null, "Error: Only Lab Technicians can access this item.");
            return false;
        }
        
        if (item.getQuantity() < quantity) {
            JOptionPane.showMessageDialog(null, "Error: Only " + item.getQuantity() + " available.");
            return false;
        }
        
        if (itemManager.decreaseQuantity(itemName, quantity)) {
            Transaction t = new Transaction(userId, itemName, quantity);
            transactions.add(t);
            JOptionPane.showMessageDialog(null, "Checked out successfully!");
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
            JOptionPane.showMessageDialog(null, "Error: No active checkout found.");
            return false;
        }
        
        if (quantity > active.getQuantity()) {
            JOptionPane.showMessageDialog(null, "Error: You only borrowed " + active.getQuantity());
            return false;
        }
        
        active.markAsReturned();
        itemManager.increaseQuantity(itemName, quantity);
        
        JOptionPane.showMessageDialog(null, "Checked in successfully!");
        return true;
    }
    
    public void viewUserBorrowedItems(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Your Borrowed Items ---\n");
        boolean found = false;
        
        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId) && !t.isReturned()) {
                sb.append(t.toString()).append("\n");
                found = true;
            }
        }
        
        if (!found) {
            sb.append("None.");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void viewTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Transaction History ---\n");
        if (transactions.isEmpty()) {
            sb.append("No transaction history.");
            JOptionPane.showMessageDialog(null, sb.toString());
            return;
        }
        for (Transaction t : transactions) {
            sb.append(t.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void viewActiveCheckouts() {
        System.out.println("\n--- Active Checkouts ---");
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Active Checkouts ---\n");
        boolean found = false;
        for (Transaction t : transactions) {
            if (!t.isReturned()) {
                sb.append(t.toString()).append("\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("No active checkouts.");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}