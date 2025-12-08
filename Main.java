import javax.swing.*;
import java.util.ArrayList;

public class Main {

    // Core Managers
    private static ItemManager itemManager;
    private static CheckInOutManager checkInOutManager;
    
    // Configuration
    private static final String LAB_TECH_CODE = "423881";

    public static void main(String[] args) {
        
        // 1. Initialize Logic
        itemManager = new ItemManager();
        checkInOutManager = new CheckInOutManager(itemManager);
        
        // 2. Load Data (Silent loading)
        DataManager.loadData(itemManager, checkInOutManager);

        // 3. Main System Loop (Login Screen)
        boolean systemRunning = true;
        while (systemRunning) {
            String[] roles = {"Student", "Lab Technician", "Exit System"};
            
            // Show Login Selection Box
            int roleChoice = JOptionPane.showOptionDialog(null, 
                "Welcome to Lab Inventory System\nSelect your position:", 
                "Lab System Login",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                null, roles, roles[0]);

            if (roleChoice == 0) { 
                // --- STUDENT LOGIN ---
                String studentId = JOptionPane.showInputDialog("Enter Student ID:");
                if (studentId != null && !studentId.trim().isEmpty()) {
                    runStudentMenu(studentId);
                }
                
            } else if (roleChoice == 1) { 
                // --- LAB TECH LOGIN ---
                String code = JOptionPane.showInputDialog("Enter 6-digit Lab Tech Code:");
                if (LAB_TECH_CODE.equals(code)) {
                    runLabTechMenu();
                } else if (code != null) {
                    JOptionPane.showMessageDialog(null, "Access Denied: Incorrect Code.", "Security Alert", JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
                // --- EXIT ---
                saveAndExit();
                systemRunning = false;
            }
        }
    }

    // ==========================================
    //           STUDENT MENU LOGIC
    // ==========================================
    private static void runStudentMenu(String userId) {
        String[] options = {
            "View Available Equipment", 
            "Search Inventory",     // <-- NEW
            "Check-Out Item", 
            "Check-In Item", 
            "View My Borrowed Items", 
            "Logout"
        };
        
        while (true) {
            String choice = (String) JOptionPane.showInputDialog(null, 
                "Logged in as Student: " + userId + "\nWhat would you like to do?", 
                "Student Dashboard", 
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == null || choice.equals("Logout")) return;

            switch (choice) {
                case "View Available Equipment":
                    showItemList(true); // true = Student items only
                    break;
                case "Search Inventory":
                    performSearch(true); // true = Filter for students
                    break;
                case "Check-Out Item":
                    performCheckOut(userId, false); // false = not a tech
                    break;
                case "Check-In Item":
                    performCheckIn(userId);
                    break;
                case "View My Borrowed Items":
                    showMyItems(userId);
                    break;
            }
        }
    }

    // ==========================================
    //           LAB TECH MENU LOGIC
    // ==========================================
    private static void runLabTechMenu() {
        String[] options = {
            "View Full Inventory", 
            "Search Inventory",     // <-- NEW
            "Add New Item", 
            "Remove Item", 
            "Check-Out (Admin)", 
            "Check-In (Admin)", 
            "View Transaction History", 
            "View Active Checkouts", 
            "Logout"
        };
        
        while (true) {
            String choice = (String) JOptionPane.showInputDialog(null, 
                "Logged in as Lab Technician (Admin)\nSelect Action:", 
                "Admin Dashboard", 
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == null || choice.equals("Logout")) return;

            switch (choice) {
                case "View Full Inventory":
                    showItemList(false); // false = Show everything
                    break;
                case "Search Inventory":
                    performSearch(false); // false = Search everything
                    break;
                case "Add New Item":
                    performAddItem();
                    break;
                case "Remove Item":
                    performRemoveItem();
                    break;
                case "Check-Out (Admin)":
                    performCheckOut("TECH_ADMIN", true);
                    break;
                case "Check-In (Admin)":
                    performCheckIn("TECH_ADMIN");
                    break;
                case "View Transaction History":
                    showTransactionHistory();
                    break;
                case "View Active Checkouts":
                    showActiveCheckouts();
                    break;
            }
        }
    }

    // ==========================================
    //           HELPER ACTIONS
    // ==========================================

    private static void performSearch(boolean studentOnly) {
        String[] searchTypes = {"Search by Name", "Search by Category"};
        int type = JOptionPane.showOptionDialog(null, "How do you want to search?", "Search Inventory",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, searchTypes, searchTypes[0]);

        if (type == -1) return; // User closed window

        String term = JOptionPane.showInputDialog("Enter search term:");
        if (term == null || term.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder("--- Search Results: '" + term + "' ---\n\n");
        ArrayList<Item> items = itemManager.getItems();
        boolean found = false;

        for (Item item : items) {
            // Filter: If studentOnly is true, skip restricted items
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) continue;

            // Logic: Check Name OR Category based on selection
            boolean match = false;
            if (type == 0) { // Name
                match = item.getName().toLowerCase().contains(term.toLowerCase());
            } else { // Category
                match = item.getCategory().toLowerCase().contains(term.toLowerCase());
            }

            if (match) {
                sb.append("• ").append(item.getName())
                  .append(" | Qty: ").append(item.getQuantity())
                  .append(" | Category: ").append(item.getCategory())
                  .append("\n");
                found = true;
            }
        }

        if (!found) sb.append("No items found matching your search.");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setColumns(40);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showItemList(boolean studentOnly) {
        StringBuilder sb = new StringBuilder("--- Inventory List ---\n\n");
        ArrayList<Item> items = itemManager.getItems();
        boolean found = false;

        for (Item item : items) {
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) continue;
            
            sb.append("• ").append(item.getName())
              .append(" | Qty: ").append(item.getQuantity())
              .append(" | Category: ").append(item.getCategory())
              .append("\n");
            found = true;
        }

        if (!found) sb.append("No items found.");
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setColumns(40);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showMyItems(String userId) {
        StringBuilder sb = new StringBuilder("--- Items Currently Borrowed by " + userId + " ---\n\n");
        ArrayList<Transaction> transactions = checkInOutManager.getTransactions();
        boolean found = false;

        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId) && !t.isReturned()) {
                sb.append("• ").append(t.getItemName())
                  .append(" (Qty: ").append(t.getQuantity()).append(")")
                  .append(" - Out: ").append(t.getCheckOutTime().toLocalDate())
                  .append("\n");
                found = true;
            }
        }
        if (!found) sb.append("You have no unreturned items.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void showTransactionHistory() {
        StringBuilder sb = new StringBuilder("--- Full Transaction History ---\n\n");
        ArrayList<Transaction> transactions = checkInOutManager.getTransactions();
        
        if (transactions.isEmpty()) sb.append("No history available.");

        for (Transaction t : transactions) {
            sb.append(t.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(20);
        textArea.setColumns(60);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Transaction Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showActiveCheckouts() {
        StringBuilder sb = new StringBuilder("--- Currently Borrowed Items (Active) ---\n\n");
        ArrayList<Transaction> transactions = checkInOutManager.getTransactions();
        boolean found = false;

        for (Transaction t : transactions) {
            if (!t.isReturned()) {
                sb.append(t.toString()).append("\n");
                found = true;
            }
        }

        if (!found) sb.append("No active checkouts. All items are returned.");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setColumns(60);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Active Checkouts", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void performAddItem() {
        String name = JOptionPane.showInputDialog("Enter Item Name:");
        if (name == null || name.trim().isEmpty()) return;
        
        String qtyStr = JOptionPane.showInputDialog("Enter Initial Quantity:");
        if (qtyStr == null) return;
        
        String cat = JOptionPane.showInputDialog("Enter Category (e.g., Active, Passive):");
        if (cat == null) return;
        
        String[] levels = {"Student Access", "Lab Tech Only"};
        int type = JOptionPane.showOptionDialog(null, "Select Access Level:", "Security", 
                0, JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
        
        AccessLevel access = (type == 1) ? AccessLevel.LAB_TECH_ONLY : AccessLevel.STUDENT_ACCESS;
        
        try {
            int qty = Integer.parseInt(qtyStr);
            itemManager.addItem(name, qty, cat, access);
            JOptionPane.showMessageDialog(null, "Success: Item '" + name + "' added to inventory.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Quantity must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void performRemoveItem() {
        String name = JOptionPane.showInputDialog("Enter exact name of item to remove:");
        if (name != null) {
            if (itemManager.removeItem(name)) {
                JOptionPane.showMessageDialog(null, "Success: Item removed.");
            } else {
                JOptionPane.showMessageDialog(null, "Error: Item not found.");
            }
        }
    }

    private static void performCheckOut(String userId, boolean isTech) {
        String name = JOptionPane.showInputDialog("Enter Item Name to Borrow:");
        if (name == null) return;
        
        String qtyStr = JOptionPane.showInputDialog("Enter Quantity:");
        if (qtyStr == null) return;

        try {
            int qty = Integer.parseInt(qtyStr);
            boolean success = checkInOutManager.checkOut(userId, name, qty, isTech);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Check-Out Successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Check-Out Failed.\nReasons:\n1. Item name incorrect?\n2. Not enough stock?\n3. Restricted item?", "Transaction Failed", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
        }
    }

    private static void performCheckIn(String userId) {
        String name = JOptionPane.showInputDialog("Enter Item Name to Return:");
        if (name == null) return;
        
        String qtyStr = JOptionPane.showInputDialog("Enter Quantity to Return:");
        if (qtyStr == null) return;

        try {
            int qty = Integer.parseInt(qtyStr);
            boolean success = checkInOutManager.checkIn(userId, name, qty);
            
            if (success) {
                JOptionPane.showMessageDialog(null, "Item Returned Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Return Failed.\nEnsure you have an active borrow record for this item.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
        }
    }

    private static void saveAndExit() {
        DataManager.saveInventory(itemManager.getItems());
        DataManager.saveTransactions(checkInOutManager.getTransactions());
        JOptionPane.showMessageDialog(null, "System Data Saved.\nGoodbye!");
        System.exit(0);
    }
}