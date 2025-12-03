import java.util.ArrayList;

public class ItemManager {

    private ArrayList<String> items = new ArrayList<>();

    // Add items
    public void addItem(String item) {
        items.add(item);
        System.out.println("Item added!");
    }

    // remove items
    public void removeItem(String item) {
        if (items.remove(item)){
            System.out.println("Item removed successfully!");
        } else {
            System.out.println("Item not found.");
        }
    }

    // view items
    public void viewItems() {
        System.out.println("\n--- Current Items ---");

        if (items.isEmpty()){
            System.out.println("No items in the inventory.");
        } else {
            for (int i = 0; i < items.size(); i++){
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }
}
