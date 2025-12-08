import java.util.ArrayList;
import java.util.Optional;

public class ItemManager {

    private ArrayList<Item> items = new ArrayList<>();

    // Add item
    public void addItem(String name, int quantity, String category, AccessLevel accessLevel) {
        Item newItem = new Item(name, quantity, category, accessLevel);
        items.add(newItem);
        System.out.println("Item added successfully!");
    }

    // Remove item by name
    public boolean removeItem(String name) {
        Optional<Item> itemToRemove = findItemByName(name);
        if (itemToRemove.isPresent()) {
            items.remove(itemToRemove.get());
            System.out.println("Item removed successfully!");
            return true;
        } else {
            System.out.println("Item not found.");
            return false;
        }
    }

    // View all items
    public void viewItems() {
        System.out.println("\n--- Current Inventory ---");
        if (items.isEmpty()) {
            System.out.println("No items in the inventory.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        }
    }

    // View items accessible by students
    public void viewStudentAccessibleItems() {
        System.out.println("\n--- Available Equipment (Student Access) ---");
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getAccessLevel() == AccessLevel.STUDENT_ACCESS) {
                System.out.println((i + 1) + ". " + items.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student-accessible items available.");
        }
    }

    // Search items by name
    public void searchByName(String searchTerm, boolean studentOnly) {
        System.out.println("\n--- Search Results (Name: " + searchTerm + ") ---");
        boolean found = false;
        int count = 1;
        
        for (Item item : items) {
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) {
                continue;
            }
            
            if (item.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                System.out.println(count + ". " + item);
                found = true;
                count++;
            }
        }
        
        if (!found) {
            System.out.println("No items found matching '" + searchTerm + "'");
        }
    }

    // Search items by category
    public void searchByCategory(String category, boolean studentOnly) {
        System.out.println("\n--- Search Results (Category: " + category + ") ---");
        boolean found = false;
        int count = 1;
        
        for (Item item : items) {
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) {
                continue;
            }
            
            if (item.getCategory().toLowerCase().contains(category.toLowerCase())) {
                System.out.println(count + ". " + item);
                found = true;
                count++;
            }
        }
        
        if (!found) {
            System.out.println("No items found in category '" + category + "'");
        }
    }

    // Find item by name
    public Optional<Item> findItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    // Update item quantity
    public boolean updateQuantity(String name, int newQuantity) {
        Optional<Item> item = findItemByName(name);
        if (item.isPresent()) {
            item.get().setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    // Decrease quantity (for checkout)
    public boolean decreaseQuantity(String name, int amount) {
        Optional<Item> item = findItemByName(name);
        if (item.isPresent()) {
            return item.get().decreaseQuantity(amount);
        }
        return false;
    }

    // Increase quantity (for checkin)
    public void increaseQuantity(String name, int amount) {
        Optional<Item> item = findItemByName(name);
        if (item.isPresent()) {
            item.get().increaseQuantity(amount);
        }
    }

    // Get all items
    public ArrayList<Item> getItems() {
        return items;
    }
}