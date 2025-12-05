import java.util.Scanner;

public class Main {

    private static final String LAB_TECH_CODE = "423881";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ItemManager itemManager = new ItemManager();
        CheckInOutManager checkInOutManager = new CheckInOutManager(itemManager);
        
        // Login/Role Selection
        System.out.println("\n=== LAB INVENTORY SYSTEM ===");
        System.out.println("Select your position:");
        System.out.println("1. Student");
        System.out.println("2. Lab Technician");
        System.out.print("Enter choice: ");
        int roleChoice = input.nextInt();
        input.nextLine(); // clear buffer
        
        String userId = "";
        boolean isLabTech = false;
        
        if (roleChoice == 1) {
            // Student login
            System.out.print("Enter your Student ID: ");
            userId = input.nextLine();
            System.out.println("Welcome, Student " + userId + "!");
            isLabTech = false;
            
        } else if (roleChoice == 2) {
            // Lab Technician login
            boolean codeValid = false;
            
            for (int attempts = 0; attempts < 3; attempts++) {
                System.out.print("Enter 6-digit Lab Technician Code: ");
                String code = input.nextLine();
                
                if (code.equals(LAB_TECH_CODE)) {
                    System.out.println("Access granted. Welcome, Lab Technician!");
                    isLabTech = true;
                    codeValid = true;
                    break;
                } else {
                    if (attempts < 2) {
                        System.out.println("Invalid code. Try again. (" + (2 - attempts) + " attempts remaining)");
                    }
                }
            }
            
            if (!codeValid) {
                System.out.println("Access denied. Exiting system...");
                input.close();
                return;
            }
            
        } else {
            System.out.println("Invalid choice. Exiting system...");
            input.close();
            return;
        }
        
        // Main menu loop
        int choice;
        do {
            System.out.println("\n=== MAIN MENU ===");
            
            if (isLabTech) {
                // Lab Technician Menu
                System.out.println("1. View All Equipment");
                System.out.println("2. Add Item");
                System.out.println("3. Remove Item");
                System.out.println("4. Check-Out Equipment");
                System.out.println("5. Check-In Equipment");
                System.out.println("6. View All Transactions");
                System.out.println("7. View Active Checkouts");
                System.out.println("8. Exit");
                
            } else {
                // Student Menu
                System.out.println("1. View Available Equipment");
                System.out.println("2. Check-Out Equipment");
                System.out.println("3. Check-In Equipment");
                System.out.println("4. View My Borrowed Items");
                System.out.println("5. Exit");
            }
            
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // clear buffer

            if (isLabTech) {
                // Lab Technician actions
                switch (choice) {
                    case 1:
                        itemManager.viewItems();
                        break;
                        
                    case 2:
                        System.out.print("Enter item name: ");
                        String newItemName = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int newItemQty = input.nextInt();
                        input.nextLine();
                        System.out.println("Select access level:");
                        System.out.println("1. Student Access (All users)");
                        System.out.println("2. Lab Technician Only");
                        System.out.print("Enter choice: ");
                        int accessChoice = input.nextInt();
                        input.nextLine();
                        
                        AccessLevel accessLevel = (accessChoice == 2) ? 
                            AccessLevel.LAB_TECH_ONLY : AccessLevel.STUDENT_ACCESS;
                        
                        itemManager.addItem(newItemName, newItemQty, accessLevel);
                        break;
                        
                    case 3:
                        System.out.print("Enter item name to remove: ");
                        String itemToRemove = input.nextLine();
                        itemManager.removeItem(itemToRemove);
                        break;
                        
                    case 4:
                        System.out.print("Enter item name to check out: ");
                        String checkoutItem = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int checkoutQty = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter your Lab Tech ID: ");
                        String labTechId = input.nextLine();
                        checkInOutManager.checkOut(labTechId, checkoutItem, checkoutQty, true);
                        break;
                        
                    case 5:
                        System.out.print("Enter item name to check in: ");
                        String checkinItem = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int checkinQty = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter your Lab Tech ID: ");
                        String labTechIdReturn = input.nextLine();
                        checkInOutManager.checkIn(labTechIdReturn, checkinItem, checkinQty);
                        break;
                        
                    case 6:
                        checkInOutManager.viewTransactionHistory();
                        break;
                        
                    case 7:
                        checkInOutManager.viewActiveCheckouts();
                        break;
                        
                    case 8:
                        System.out.println("Exiting system...");
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
                
            } else {
                // Student actions
                switch (choice) {
                    case 1:
                        itemManager.viewStudentAccessibleItems();
                        break;
                        
                    case 2:
                        System.out.print("Enter item name to check out: ");
                        String checkoutItem = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int checkoutQty = input.nextInt();
                        input.nextLine();
                        checkInOutManager.checkOut(userId, checkoutItem, checkoutQty, false);
                        break;
                        
                    case 3:
                        System.out.print("Enter item name to check in: ");
                        String checkinItem = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int checkinQty = input.nextInt();
                        input.nextLine();
                        checkInOutManager.checkIn(userId, checkinItem, checkinQty);
                        break;
                        
                    case 4:
                        checkInOutManager.viewUserBorrowedItems(userId);
                        break;
                        
                    case 5:
                        System.out.println("Exiting system...");
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } while ((isLabTech && choice != 8 ) || (!isLabTech && choice != 5));

        input.close();
    }
}