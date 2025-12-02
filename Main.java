import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ItemManager manager = new ItemManager();
        int choice;

        do {
            System.out.println("\n=== LAB INVENTORY SYSTEM ===");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. View Items");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    System.out.print("Enter item name to add: ");
                    String newItem = input.nextLine();
                    manager.addItem(newItem);
                    break;

                case 2:
                    manager.removeItem("");
                    break;

                case 3:
                    manager.viewItems();
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        input.close();
    }
}
