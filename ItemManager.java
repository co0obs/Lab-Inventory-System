import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;

public class ItemManager {

    private ArrayList<Item> items = new ArrayList<>();

    // Add item
    public void addItem(String name, int quantity, String category, AccessLevel accessLevel) {
        Item newItem = new Item(name, quantity, category, accessLevel);
        items.add(newItem);
    }

    // Remove item by name
    public boolean removeItem(String name) {
        Optional<Item> itemToRemove = findItemByName(name);
        if (itemToRemove.isPresent()) {
            items.remove(itemToRemove.get());
            JOptionPane.showMessageDialog(null, "Item Removed Successfully: " + itemToRemove.get().getName());
            return true;
        } else {
            System.out.println("Item not found.");
            return false;
        }
    }

    // View all items
    public void viewItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Current Inventory ---\n");
        if (items.isEmpty()) {
            sb.append("No items in the inventory.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                sb.append((i+1) + ". " + items.get(i).toString() + "\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // View items accessible by students
    public void viewStudentAccessibleItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Available Equipment (Student Access) ---\n");
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getAccessLevel() == AccessLevel.STUDENT_ACCESS) {
                sb.append((i + 1) + ". " + items.get(i).toString() + "\n");
                found = true;
            }
        }
        if (!found) {
            sb.append("No student-accessible items available.");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Search items by name
    public void searchByName(String searchTerm, boolean studentOnly) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Search Results (Name: " + searchTerm + ") ---\n");
        boolean found = false;
        int count = 1;
        
        for (Item item : items) {
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) {
                continue;
            }
            
            if (item.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                sb.append(count + ". " + item.toString() + "\n");
                found = true;
                count++;
            }
        }
        
        if (!found) {
            sb.append("No items found matching '" + searchTerm + "'");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Search items by category
    public void searchByCategory(String category, boolean studentOnly) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Search Results (Category: " + category + ") ---\n");
        boolean found = false;
        int count = 1;
        
        for (Item item : items) {
            if (studentOnly && item.getAccessLevel() != AccessLevel.STUDENT_ACCESS) {
                continue;
            }
            
            if (item.getCategory().toLowerCase().contains(category.toLowerCase())) {
                sb.append(count + ". " + item.toString() + "\n");
                found = true;
                count++;
            }
        }
        
       if (!found) {
            sb.append("No items found in category '" + category + "'");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
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