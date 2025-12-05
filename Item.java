public class Item {

    private int id;
    private String name;
    private int quantity;
    private String category;
    private AccessLevel accessLevel;

    public Item(int id, String name, int quantity, String category, AccessLevel accessLevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean decreaseQuantity(int amount) {
        if (amount <= 0 || amount > quantity) {
            return false;
        }
        quantity -= amount;
        return true;
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public String toString() {
        return name + " (Qty: " + quantity + ", Category: " + category + ", Access: " + accessLevel + ")";
    }
}