import javax.swing.JOptionPane;

public class Main {

    private static final String LAB_TECH_CODE = "423881";

    public static void main(String[] args) {

        // Initialize managers
        ItemManager itemManager = new ItemManager();
        CheckInOutManager checkInOutManager = new CheckInOutManager(itemManager);

        // Load data
        //DataManager.loadData(itemManager, checkInOutManager);

        // Role selection via GUI
        String[] roles = {"Student", "Lab Technician"};
        int roleChoice = JOptionPane.showOptionDialog(null,
                "Select your position:",
                "LAB INVENTORY SYSTEM",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                roles[0]);

        if (roleChoice == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String userId = "";
        boolean isLabTech = false;

        if (roleChoice == 0) { // Student
            userId = JOptionPane.showInputDialog("Enter your Student ID:");
            if (userId == null) return;
            isLabTech = false;
            JOptionPane.showMessageDialog(null, "Welcome, Student " + userId + "!");
        } else { // Lab Technician
            boolean ok = false;
            for (int attempt = 0; attempt < 3; attempt++) {
                String code = JOptionPane.showInputDialog("Enter 6-digit Lab Technician Code:");
                if (code == null) return;
                if (code.equals(LAB_TECH_CODE)) {
                    isLabTech = true;
                    ok = true;
                    JOptionPane.showMessageDialog(null, "Access granted.");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect. Attempts left: " + (2 - attempt));
                }
            }
            if (!ok) {
                JOptionPane.showMessageDialog(null, "Access denied.");
                return;
            }
            userId = JOptionPane.showInputDialog("Enter your Lab Tech ID:");
            if (userId == null) return;
        }

        // Main loop - GUI menu
        int choice;
        do {
            if (isLabTech) {
                DataManager.loadData(itemManager, checkInOutManager);
                String menu = "1. View All Equipment\n2. Add Item\n3. Remove Item\n4. Check-Out Equipment\n5. Check-In Equipment\n6. View All Transactions\n7. View Active Checkouts\n8. Exit";
                String sel = JOptionPane.showInputDialog(menu);
                if (sel == null) break;
                choice = Integer.parseInt(sel);
                switch (choice) {
                    case 1:
                        itemManager.viewItems();
                        break;
                    case 2:
                        String name = JOptionPane.showInputDialog("Enter item name:");
                        if (name == null) break;
                        int qty = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                        String category = JOptionPane.showInputDialog("Enter category:");
                        int a = JOptionPane.showOptionDialog(null, "Access level:", "Access Level",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                new String[]{"Student", "Lab Tech Only"}, "Student");
                        AccessLevel access = (a == 1 ? AccessLevel.LAB_TECH_ONLY : AccessLevel.STUDENT_ACCESS);
                        itemManager.addItem(name, qty, category, access);
                        break;
                    case 3:
                        String toRemove = JOptionPane.showInputDialog("Enter item name to remove:");
                        if (toRemove == null) break;
                        itemManager.removeItem(toRemove);
                        break;
                    case 4:
                        String outName = JOptionPane.showInputDialog("Enter item name:");
                        if (outName == null) break;
                        int outQty = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                        String techId = JOptionPane.showInputDialog("Enter your Lab Tech ID:");
                        if (techId == null) break;
                        checkInOutManager.checkOut(techId, outName, outQty, true);
                        break;
                    case 5:
                        String inName = JOptionPane.showInputDialog("Enter item name:");
                        if (inName == null) break;
                        int inQty = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                        String tID = JOptionPane.showInputDialog("Enter your Lab Tech ID:");
                        if (tID == null) break;
                        checkInOutManager.checkIn(tID, inName, inQty);
                        break;
                    case 6:
                        checkInOutManager.viewTransactionHistory();
                        break;
                    case 7:
                        checkInOutManager.viewActiveCheckouts();
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice.");
                }
            } else {
                String menu = "1. View Available Equipment\n2. Check-Out Equipment\n3. Check-In Equipment\n4. View My Borrowed Items\n5. Exit";
                String sel = JOptionPane.showInputDialog(menu);
                if (sel == null) break;
                choice = Integer.parseInt(sel);
                switch (choice) {
                    case 1:
                        itemManager.viewStudentAccessibleItems();
                        break;
                    case 2:
                        String sOut = JOptionPane.showInputDialog("Enter item name:");
                        if (sOut == null) break;
                        int sQty = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                        checkInOutManager.checkOut(userId, sOut, sQty, false);
                        break;
                    case 3:
                        String sIn = JOptionPane.showInputDialog("Enter item name:");
                        if (sIn == null) break;
                        int sInQty = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
                        checkInOutManager.checkIn(userId, sIn, sInQty);
                        break;
                    case 4:
                        checkInOutManager.viewUserBorrowedItems(userId);
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice.");
                }
            }
        } while ((isLabTech && choice != 8) || (!isLabTech && choice != 5));

        // Save both inventory and transactions before exiting
        DataManager.saveInventory(itemManager.getItems());
        DataManager.saveTransactions(checkInOutManager.getTransactions());
    }

    // Helper method (kept but not needed for GUI because dialogs are inline)
    private static void showInventoryMenu(ItemManager itemManager, boolean studentOnly) {
        String[] options = {"Search by Name", "Search by Category", "View All", "Cancel"};
        int opt = JOptionPane.showOptionDialog(null, "Inventory Options", "Inventory",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (opt == 0) {
            String searchName = JOptionPane.showInputDialog("Enter item name to search:");
            if (searchName != null) itemManager.searchByName(searchName, studentOnly);
        } else if (opt == 1) {
            String searchCategory = JOptionPane.showInputDialog("Enter category to search:");
            if (searchCategory != null) itemManager.searchByCategory(searchCategory, studentOnly);
        } else if (opt == 2) {
            if (studentOnly) itemManager.viewStudentAccessibleItems();
            else itemManager.viewItems();
        }
    }
}