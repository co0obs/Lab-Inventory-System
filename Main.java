import java.util.Scanner;

public class Main {

    private static final String LAB_TECH_CODE = "423881";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ItemManager itemManager = new ItemManager();
        CheckInOutManager checkInOutManager = new CheckInOutManager(itemManager);
        
        System.out.println("\n=== LAB INVENTORY SYSTEM ===");
        System.out.println("Select your position:");
        System.out.println("1. Student");
        System.out.println("2. Lab Technician");
        System.out.print("Enter choice: ");
        int roleChoice = input.nextInt();
        input.nextLine();
        
        String userId = "";
        boolean isLabTech = false;
        
        if (roleChoice == 1) {
            System.out.print("Enter your Student ID: ");
            userId = input.nextLine();
            isLabTech = false;
            System.out.println("Welcome, Student " + userId + "!");
            
        } else if (roleChoice == 2) {
            boolean ok = false;
            for (int attempt = 0; attempt < 3; attempt++) {
                System.out.print("Enter 6-digit Lab Technician Code: ");
                String code = input.nextLine();
                if (code.equals(LAB_TECH_CODE)) {
                    isLabTech = true;
                    ok = true;
                    System.out.println("Access granted.");
                    break;
                } else {
                    System.out.println("Incorrect. Attempts left: " + (2 - attempt));
                }
            }
            if (!ok) {
                System.out.println("Access denied.");
                input.close();
                return;
            }
        } else {
            System.out.println("Invalid choice.");
            input.close();
            return;
        }
        
        int choice;
        
        do {
            System.out.println("\n=== MAIN MENU ===");

            if (isLabTech) {
                System.out.println("1. View All Equipment");
                System.out.println("2. Add Item");
                System.out.println("3. Remove Item");
                System.out.println("4. Check-Out Equipment");
                System.out.println("5. Check-In Equipment");
                System.out.println("6. View All Transactions");
                System.out.println("7. View Active Checkouts");
                System.out.println("8. Exit");
            } else {
                System.out.println("1. View Available Equipment");
                System.out.println("2. Check-Out Equipment");
                System.out.println("3. Check-In Equipment");
                System.out.println("4. View My Borrowed Items");
                System.out.println("5. Exit");
            }
            
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (isLabTech) {
                switch (choice) {
                    case 1:
                        itemManager.viewItems();
                        break;

                    case 2:
                        System.out.print("Enter item name: ");
                        String name = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int qty = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter category: ");
                        String category = input.nextLine();
                        System.out.println("Access level: (1) Student (2) Lab tech only");
                        int a = input.nextInt();
                        input.nextLine();
                        AccessLevel access = (a == 2 ? AccessLevel.LAB_TECH_ONLY : AccessLevel.STUDENT_ACCESS);
                        itemManager.addItem(name, qty, category, access);
                        break;

                    case 3:
                        System.out.print("Enter item name to remove: ");
                        itemManager.removeItem(input.nextLine());
                        break;

                    case 4:
                        System.out.print("Enter item name: ");
                        String outName = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int outQty = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter your Lab Tech ID: ");
                        String techId = input.nextLine();
                        checkInOutManager.checkOut(techId, outName, outQty, true);
                        break;

                    case 5:
                        System.out.print("Enter item name: ");
                        String inName = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int inQty = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter your Lab Tech ID: ");
                        String tID = input.nextLine();
                        checkInOutManager.checkIn(tID, inName, inQty);
                        break;

                    case 6:
                        checkInOutManager.viewTransactionHistory();
                        break;

                    case 7:
                        checkInOutManager.viewActiveCheckouts();
                        break;

                    case 8:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } else {
                switch (choice) {
                    case 1:
                        itemManager.viewStudentAccessibleItems();
                        break;

                    case 2:
                        System.out.print("Enter item name: ");
                        String sOut = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int sQty = input.nextInt();
                        input.nextLine();
                        checkInOutManager.checkOut(userId, sOut, sQty, false);
                        break;

                    case 3:
                        System.out.print("Enter item name: ");
                        String sIn = input.nextLine();
                        System.out.print("Enter quantity: ");
                        int sInQty = input.nextInt();
                        input.nextLine();
                        checkInOutManager.checkIn(userId, sIn, sInQty);
                        break;

                    case 4:
                        checkInOutManager.viewUserBorrowedItems(userId);
                        break;

                    case 5:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } while ((isLabTech && choice != 8) || (!isLabTech && choice != 5));

        input.close();
    }
}
