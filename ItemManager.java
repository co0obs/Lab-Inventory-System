import java.util.ArrayList;

public class ItemManager {

    private ArrayList<String> items = new ArrayList<>();

    // Add item
    public void addItem(String item) {
        items.add(item);
        System.out.println("Item added!");
    }

    // placeholder: remove + view 
    public void removeItem(String item) {
        System.out.println("Remove feature not yet implemented.");
    }

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
