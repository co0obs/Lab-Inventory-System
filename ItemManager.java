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
        System.out.println("View feature not yet implemented.");
    }
}
