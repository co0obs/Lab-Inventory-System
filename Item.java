public class Item {
    
    private String name;
    private int quantity;
    private AccessLevel accessLevel;
    
    // Constructor
    public Item(String name, int quantity, AccessLevel accessLevel) {
        this.name = name;
        this.quantity = quantity;
        this.accessLevel = accessLevel;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    // Utility methods
    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }
    
    public boolean decreaseQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false;
    }
    
    public boolean isAvailable() {
        return quantity > 0;
    }
    
    @Override
    public String toString() {
        String access = (accessLevel == AccessLevel.LAB_TECH_ONLY) ? "Lab Tech Only" : "All Users";
        return name + " | Qty: " + quantity + " | Access: " + access;
    }
}