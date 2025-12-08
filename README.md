# Lab Inventory System

## Overview
The **Lab Inventory System** is a Java-based command-line application designed to help laboratories efficiently manage equipment, supplies, and consumables. It ensures proper tracking of usage, prevents loss or misplacement of items, and provides visibility into stock levels. The system supports role-based access control, distinguishing between Students and Lab Technicians to ensure secure management of sensitive equipment.

## Features

### 1. Inventory Management
* **Stock Tracking:** Real-time tracking of equipment names, quantities, categories, and access levels.
* **Search Functionality:** Find items quickly by **Name** or **Category**.
* **Smart Updates:** Adding an existing item automatically updates the quantity rather than creating duplicates.
* **Categorization:** Items are organized by category (e.g., Active Components, Passive Components).

### 2. Transaction System
* **Check-In / Check-Out:** Users can borrow items (reducing stock) and return them (restoring stock).
* **Transaction History:** A complete log of all borrowing activities is maintained, recording the User ID, Item Name, Quantity, and Timestamps.
* **Data Persistence:**
    * **Inventory Data:** Saved to `inventory.dat`.
    * **Transaction History:** Saved to `transactions.dat`.
    * *Note: Data is automatically loaded at startup and saved upon exit to prevent data loss.*

### 3. Role-Based Access Control
The system provides two distinct user roles:

* **Student**
    * Login via Student ID.
    * Can search and view "Student Access" equipment.
    * Can borrow and return items.
    * Can view their own borrowed items list.
* **Lab Technician**
    * **Secured Access:** Requires a 6-digit security code (`423881`) to login.
    * **Full Control:** Can add new items or remove old ones.
    * **Full Visibility:** Can view the complete transaction history and all active checkouts.
    * **Restricted Access:** Can manage and borrow "Lab Tech Only" equipment.

---
## File Structure
* **`Main.java`**: The entry point of the application handling user login and menus.
* **`Item.java`**: Data model representing a piece of equipment.
* **`ItemManager.java`**: Logic for managing inventory (CRUD operations, Search).
* **`Transaction.java`**: Data model representing a borrow/return event with timestamps.
* **`CheckInOutManager.java`**: Logic for processing borrowing and returning of items.
* **`DataManager.java`**: Handles File I/O (saving and loading data).
* **`AccessLevel.java`**: Enum defining permissions (`STUDENT_ACCESS`, `LAB_TECH_ONLY`).
