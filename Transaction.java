import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    
    private String userId;
    private String itemName;
    private int quantity;
    private LocalDateTime checkOutTime;
    private LocalDateTime checkInTime;
    private boolean isReturned;
    
    // Constructor for new check-outs
    public Transaction(String userId, String itemName, int quantity) {
        this.userId = userId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.checkOutTime = LocalDateTime.now();
        this.checkInTime = null;
        this.isReturned = false;
    }

    // Constructor for loading from file
    public Transaction(String userId, String itemName, int quantity, LocalDateTime checkOutTime, LocalDateTime checkInTime, boolean isReturned) {
        this.userId = userId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.checkOutTime = checkOutTime;
        this.checkInTime = checkInTime;
        this.isReturned = isReturned;
    }
    
    // Mark as returned
    public void markAsReturned() {
        this.checkInTime = LocalDateTime.now();
        this.isReturned = true;
    }
    
    // Getters
    public String getUserId() {
        return userId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }
    
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    
    public boolean isReturned() {
        return isReturned;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String status = isReturned ? "Returned" : "Borrowed";
        String checkInStr = isReturned ? " | Returned: " + checkInTime.format(formatter) : "";
        
        return "User: " + userId + " | Item: " + itemName + " | Qty: " + quantity + 
               " | Checked Out: " + checkOutTime.format(formatter) + checkInStr + " | Status: " + status;
    }
}