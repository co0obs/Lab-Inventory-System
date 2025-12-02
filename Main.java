import java.util.Scanner;

public class Main {

    private static final String LAB_TECH_CODE = "423881";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ItemManager manager = new ItemManager();
        
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
            System.out.print("Enter 6-digit Lab Technician Code: ");
            String code = input.nextLine();
            
            if (code.equals(LAB_TECH_CODE)) {
                System.out.println("Access granted. Welcome, Lab Technician!");
                isLabTech = true;
            } else {
                System.out.println("Invalid code. Access denied. Exiting system...");
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
                System.out.println("1. Check-In Equipment");
                System.out.println("2. Check-Out Equipment");
                System.out.println("3. View Equipment");
                System.out.println("4. Add Item");
                System.out.println("5. Remove Item");
                System.out.println("6. Exit");
                
            } else {
                // Student Menu
                System.out.println("1. View Equipment/Lab Materials");
                System.out.println("2. Check-In Equipment");
                System.out.println("3. Check-Out Equipment");
                System.out.println("4. Exit");
            }
            
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // clear buffer

            if (isLabTech) {
                // Lab Technician actions
                switch (choice) {
                    case 1:
                        System.out.println("[Check-In feature - Coming soon]");
                        break;
                    case 2:
                        System.out.println("[Check-Out feature - Coming soon]");
                        break;
                    case 3:
                        manager.viewItems();
                        break;
                    case 4:
                        System.out.print("Enter item name to add: ");
                        String newItem = input.nextLine();
                        manager.addItem(newItem);
                        break;
                    case 5:
                        System.out.print("Enter item name to remove: ");
                        String itemToRemove = input.nextLine();
                        manager.removeItem(itemToRemove);
                        break;
                    case 6:
                        System.out.println("Exiting system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
                
            } else {
                // Student actions
                switch (choice) {
                    case 1:
                        manager.viewItems();
                        break;
                    case 2:
                        System.out.println("[Check-In feature - Coming soon]");
                        System.out.println("Your ID: " + userId);
                        break;
                    case 3:
                        System.out.println("[Check-Out feature - Coming soon]");
                        System.out.println("Your ID: " + userId);
                        break;
                    case 4:
                        System.out.println("Exiting system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } while ((isLabTech && choice != 6) || (!isLabTech && choice != 4));

        input.close();
    }
}