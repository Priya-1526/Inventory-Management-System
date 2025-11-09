import java.io.*;
import java.util.*;

class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    String name;
    int quantity;
    double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Qty: " + quantity +
               " | Price: " + price;
    }
}

public class InventorySystem {

    private static final String FILE_NAME = "inventory.dat";
    private static HashMap<Integer, Product> inventory = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadInventory();
        int choice;

        while (true) {
            System.out.println("\n====== INVENTORY MANAGEMENT SYSTEM ======");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Increase Stock");
            System.out.println("5. Decrease Stock");
            System.out.println("6. Search Product");
            System.out.println("7. View All Products");
            System.out.println("8. Save & Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> increaseStock();
                case 5 -> decreaseStock();
                case 6 -> searchProduct();
                case 7 -> viewAllProducts();
                case 8 -> {
                    saveInventory();
                    System.out.println("Data saved. Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Add product
    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        if (inventory.containsKey(id)) {
            System.out.println("Product with this ID already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        Product p = new Product(id, name, qty, price);
        inventory.put(id, p);

        System.out.println("Product added successfully!");
    }

    // Update product
    private static void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        int id = sc.nextInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found!");
            return;
        }

        Product p = inventory.get(id);
        sc.nextLine();

        System.out.print("Enter new name (" + p.name + "): ");
        String name = sc.nextLine();

        System.out.print("Enter new price (" + p.price + "): ");
        double price = sc.nextDouble();

        p.name = name;
        p.price = price;

        System.out.println("Product updated!");
    }

    // Delete product
    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int id = sc.nextInt();

        if (inventory.remove(id) != null) {
            System.out.println("Product deleted!");
        } else {
            System.out.println("Product not found!");
        }
    }

    // Increase stock
    private static void increaseStock() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter quantity to add: ");
        int qty = sc.nextInt();

        inventory.get(id).quantity += qty;
        System.out.println("Stock updated!");
    }

    // Decrease stock
    private static void decreaseStock() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Enter quantity to remove: ");
        int qty = sc.nextInt();

        Product p = inventory.get(id);

        if (p.quantity < qty) {
            System.out.println("Not enough stock!");
            return;
        }

        p.quantity -= qty;

        if (p.quantity < 5) {
            System.out.println("Warning: Low stock!");
        }

        System.out.println("Stock updated!");
    }

    // Search product
    private static void searchProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        if (inventory.containsKey(id)) {
            System.out.println(inventory.get(id));
        } else {
            System.out.println("Product not found!");
        }
    }

    // View all products
    private static void viewAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }

        System.out.println("\n------ CURRENT INVENTORY ------");
        for (Product p : inventory.values()) {
            System.out.println(p);
        }
    }

    // Load inventory from file
    @SuppressWarnings("unchecked")
    private static void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (HashMap<Integer, Product>) ois.readObject();
            System.out.println("Inventory loaded successfully!");
        } catch (Exception e) {
            System.out.println("Starting with empty inventory...");
        }
    }

    // Save inventory to file
    private static void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }
}
