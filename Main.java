import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<String> items = new ArrayList<>();
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

            // logic will be added in the next versions

        } while (choice != 4);

        input.close();
    }
}