# Inventory-Management-System
It’s basically meant to manage products, track quantity, and maintain records so the shop doesn’t run out of items and knows what is available.

# 🏪 Inventory Management System (Java Console Application)

A simple **Inventory Management System** built using **Java**.  
It allows users to manage products (add, update, delete, search, view, and maintain stock levels) directly from the console.  
All data is **saved permanently** using **object serialization** (`inventory.dat` file).

---

## 🧰 Features

### 🔹 Product Operations
- **Add Product** – Create a new product with ID, name, quantity, and price.  
- **Update Product** – Modify the name and price of an existing product.  
- **Delete Product** – Remove a product permanently from inventory.  
- **Search Product** – Search any product by its unique ID.  
- **View All Products** – Display a complete list of all products in the inventory.

### 🔹 Stock Management
- **Increase Stock** – Add quantity to an existing product.  
- **Decrease Stock** – Reduce quantity (shows low stock warning when below 5).

### 🔹 File Handling
- Automatically **loads inventory data** from `inventory.dat` (if exists).  
- Automatically **saves all changes** to the same file when exiting.

---

## 🧠 How It Works

1. When the program starts:
   - It tries to **load saved data** from `inventory.dat` using `ObjectInputStream`.
   - If the file doesn’t exist, a **new empty inventory** is created.

2. The system displays a **menu** with 8 options:

====== INVENTORY MANAGEMENT SYSTEM ======

1. Add Product
2. Update Product
3. Delete Product
4. Increase Stock
5. Decrease Stock
6. Search Product
7. View All Products
8. Save & Exit


3. The user enters their choice (1–8).  
The program executes the corresponding operation using a **modern switch expression** (Java 14+).

4. All products are stored in a `HashMap<Integer, Product>` for fast lookup using product ID.

5. When exiting, the inventory data is **serialized (saved)** into `inventory.dat` file using `ObjectOutputStream`.

---

## 🧩 Code Structure

InventorySystem.java
└── Product (Serializable class)
├── int id
├── String name
├── int quantity
├── double price
└── toString() → prints product details

Main Class: InventorySystem
├── addProduct()
├── updateProduct()
├── deleteProduct()
├── increaseStock()
├── decreaseStock()
├── searchProduct()
├── viewAllProducts()
├── loadInventory()
└── saveInventory()


---

## 💾 File Used

**`inventory.dat`**  
Stores serialized `HashMap<Integer, Product>` object.  
This file is automatically created and updated — **do not edit manually**.

---

## ⚙️ Requirements

- Java 14 or higher  
- Any text editor or IDE (VS Code, IntelliJ, Eclipse)

---

## ▶️ How to Run

### 🔸 Using Command Line:
```bash
javac InventorySystem.java
java InventorySystem
🔸 Using IDE:

Open the project in your IDE (e.g., IntelliJ or VS Code).

Run the InventorySystem main class.

🧪 Example Output

====== INVENTORY MANAGEMENT SYSTEM ======
1. Add Product
2. Update Product
3. Delete Product
4. Increase Stock
5. Decrease Stock
6. Search Product
7. View All Products
8. Save & Exit
Enter your choice: 1
Enter Product ID: 101
Enter Product Name: Laptop
Enter Quantity: 10
Enter Price: 45000
Product added successfully!

Enter your choice: 7

------ CURRENT INVENTORY ------
ID: 101 | Name: Laptop | Qty: 10 | Price: 45000.0

⚡ Low Stock Warning

When decreasing stock, if the quantity falls below 5,
a warning message is displayed:

Warning: Low stock!

🛡️ Error Handling

Prevents duplicate product IDs when adding.

Checks if product exists before updating/deleting.

Ensures sufficient quantity when decreasing stock.

Gracefully handles missing or corrupt data files.
