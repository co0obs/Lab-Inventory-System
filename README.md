# Lab Inventory System

## Overview
The **Lab Inventory System** is a Java-based application designed to help laboratories efficiently manage equipment, supplies, and consumables. It utilizes a **Graphical User Interface (GUI)** to provide a user-friendly experience for tracking usage, stock levels, and user activity. The system supports role-based access control, distinguishing between Students and Lab Technicians to ensure secure management of sensitive equipment.

## Features

### 1. User Interface (GUI)
* **Visual Dashboards:** Replaced old text menus with intuitive Pop-up Windows and Dropdown Menus (using Java Swing/JOptionPane).
* **Scrollable Lists:** Inventory and history logs are displayed in clean, scrollable text areas for easy reading.
* **Smart Inputs:** Validates user input (like quantities) to prevent system errors.

### 2. Inventory Management
* **Search Functionality:** Quickly find items by **Name** or **Category** using the built-in search tool.
* **Stock Tracking:** Real-time tracking of equipment names, quantities, categories, and access levels.
* **Categorization:** Items are organized by category (e.g., Active Components, Passive Components).

### 3. Transaction System
* **Check-In / Check-Out:** Users can borrow items (reducing stock) and return them (restoring stock) via simple pop-up dialogs.
* **Transaction History:** A complete log of all borrowing activities is maintained and viewable by administrators.
* **Active Checkouts:** Lab Technicians can view a filtered list of items currently missing or borrowed.
* **Data Persistence:**
    * **Inventory Data:** Saved to `inventory.dat`.
    * **Transaction History:** Saved to `transactions.dat`.
    * *Data is automatically loaded at startup and saved upon exit.*

### 4. Role-Based Access Control
The system provides two distinct user roles:

* **Student**
    * Login via Student ID.
    * Can search and view "Student Access" equipment.
    * Can borrow and return items.
    * Can view their own personal borrowed items list.
* **Lab Technician**
    * **Secured Access:** Requires a 6-digit security code (`423881`) to login.
    * **Full Control:** Can add new items, remove old ones, and view all system history.
    * **Restricted Access:** Can manage and borrow "Lab Tech Only" equipment.

---
## File Structure
* **`Main.java`**: The GUI entry point handling windows, menus, and user inputs.
* **`Item.java`**: Data model representing a piece of equipment.
* **`ItemManager.java`**: Logic for managing inventory (CRUD operations, Search).
* **`Transaction.java`**: Data model representing a borrow/return event with timestamps.
* **`CheckInOutManager.java`**: Logic for processing borrowing and returning of items.
* **`DataManager.java`**: Handles File I/O (saving and loading data).
* **`AccessLevel.java`**: Enum defining permissions (`STUDENT_ACCESS`, `LAB_TECH_ONLY`).
