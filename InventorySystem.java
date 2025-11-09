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
        double totalValue = quantity * price;
        return String.format("ID: %-5d | Name: %-20s | Qty: %-5d | Price: %-7.2f | Total: %.2f",
                             id, name, quantity, price, totalValue);
    }
}

public class InventorySystem {

    private static final String FILE_NAME = "inventory.data";
    private static HashMap<Integer, Product> inventory = new HashMap<>();
    private static final Scanner sc = new Scanner(System.in);

    // ANSI Color codes for styling
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        loadInventory();
        int choice;

        while (true) {
            System.out.println(CYAN + "\n====== INVENTORY MANAGEMENT SYSTEM ======" + RESET);
            System.out.println(BLUE + "1." + RESET + " Add Product");
            System.out.println(BLUE + "2." + RESET + " Update Product");
            System.out.println(BLUE + "3." + RESET + " Delete Product");
            System.out.println(BLUE + "4." + RESET + " Increase Stock");
            System.out.println(BLUE + "5." + RESET + " Decrease Stock");
            System.out.println(BLUE + "6." + RESET + " Search Product by ID");
            System.out.println(BLUE + "7." + RESET + " Search Product by Name");
            System.out.println(BLUE + "8." + RESET + " View All Products");
            System.out.println(BLUE + "9." + RESET + " View Low-Stock Products (<5)");
            System.out.println(BLUE + "10." + RESET + " Save & Exit");
            System.out.print(YELLOW + "Enter your choice: " + RESET);

            if (!sc.hasNextInt()) {
                System.out.println(RED + "Invalid input! Please enter a number." + RESET);
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> increaseStock();
                case 5 -> decreaseStock();
                case 6 -> searchById();
                case 7 -> searchByName();
                case 8 -> viewAllProducts();
                case 9 -> lowStockReport();
                case 10 -> {
                    saveInventory();
                    System.out.println(GREEN + "Data saved. Exiting..." + RESET);
                    System.exit(0);
                }
                default -> System.out.println(RED + "Invalid choice!" + RESET);
            }
        }
    }

    // Add product
    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = getIntInput();
        if (inventory.containsKey(id)) {
            System.out.println(RED + "Product with this ID already exists!" + RESET);
            return;
        }

        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = getIntInput();

        System.out.print("Enter Price per unit: ");
        double price = getDoubleInput();

        inventory.put(id, new Product(id, name, qty, price));
        System.out.println(GREEN + "✅ Product added successfully!" + RESET);
    }

    // Update product
    private static void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        int id = getIntInput();

        if (!inventory.containsKey(id)) {
            System.out.println(RED + "Product not found!" + RESET);
            return;
        }

        Product p = inventory.get(id);
        sc.nextLine();
        System.out.print("Enter new name (" + p.name + "): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) p.name = name;

        System.out.print("Enter new price (" + p.price + "): ");
        double price = getDoubleInput();
        if (price >= 0) p.price = price;

        System.out.println(GREEN + "✅ Product updated!" + RESET);
    }

    // Delete product
    private static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int id = getIntInput();
        if (inventory.remove(id) != null)
            System.out.println(GREEN + "✅ Product deleted!" + RESET);
        else
            System.out.println(RED + "Product not found!" + RESET);
    }

    // Increase stock
    private static void increaseStock() {
        System.out.print("Enter Product ID: ");
        int id = getIntInput();
        if (!inventory.containsKey(id)) {
            System.out.println(RED + "Product not found!" + RESET);
            return;
        }

        System.out.print("Enter quantity to add: ");
        int qty = getIntInput();
        if (qty <= 0) {
            System.out.println(RED + "Quantity must be positive!" + RESET);
            return;
        }

        inventory.get(id).quantity += qty;
        System.out.println(GREEN + "✅ Stock increased successfully!" + RESET);
    }

    // Decrease stock
    private static void decreaseStock() {
        System.out.print("Enter Product ID: ");
        int id = getIntInput();
        if (!inventory.containsKey(id)) {
            System.out.println(RED + "Product not found!" + RESET);
            return;
        }

        System.out.print("Enter quantity to remove: ");
        int qty = getIntInput();
        Product p = inventory.get(id);

        if (qty <= 0) {
            System.out.println(RED + "Quantity must be positive!" + RESET);
            return;
        }

        if (p.quantity < qty) {
            System.out.println(RED + "Not enough stock!" + RESET);
            return;
        }

        p.quantity -= qty;
        if (p.quantity < 5)
            System.out.println(YELLOW + "⚠️ Warning: Low stock!" + RESET);

        System.out.println(GREEN + "✅ Stock decreased successfully!" + RESET);
    }

    // Search by ID
    private static void searchById() {
        System.out.print("Enter Product ID: ");
        int id = getIntInput();
        if (inventory.containsKey(id))
            System.out.println(inventory.get(id));
        else
            System.out.println(RED + "Product not found!" + RESET);
    }

    // Search by name
    private static void searchByName() {
        sc.nextLine();
        System.out.print("Enter product name to search: ");
        String search = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Product p : inventory.values()) {
            if (p.name.toLowerCase().contains(search)) {
                System.out.println(p);
                found = true;
            }
        }

        if (!found)
            System.out.println(RED + "No matching products found!" + RESET);
    }

    // View all products + Total Inventory Value
    private static void viewAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println(RED + "No products in inventory." + RESET);
            return;
        }

        System.out.println(CYAN + "\n------ CURRENT INVENTORY ------" + RESET);
        System.out.printf("%-5s %-20s %-10s %-10s %-10s%n", "ID", "Name", "Qty", "Price", "Total");
        System.out.println("---------------------------------------------------------------");

        double totalInventoryValue = 0;
        for (Product p : inventory.values()) {
            double total = p.quantity * p.price;
            totalInventoryValue += total;
            System.out.printf("%-5d %-20s %-10d %-10.2f %-10.2f%n", p.id, p.name, p.quantity, p.price, total);
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf(BLUE + "Total Inventory Value: ₹%.2f%n" + RESET, totalInventoryValue);
    }

    // Low stock
    private static void lowStockReport() {
        System.out.println(YELLOW + "\n------ LOW STOCK PRODUCTS (Qty < 5) ------" + RESET);
        boolean found = false;
        for (Product p : inventory.values()) {
            if (p.quantity < 5) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found)
            System.out.println(GREEN + "All products have sufficient stock!" + RESET);
    }

    // Load data
    @SuppressWarnings("unchecked")
    private static void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inventory = (HashMap<Integer, Product>) ois.readObject();
            System.out.println(GREEN + "✅ Inventory loaded successfully!" + RESET);
        } catch (Exception e) {
            System.out.println(YELLOW + "No previous data found. Starting fresh..." + RESET);
        }
    }

    // Save data
    private static void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
        } catch (Exception e) {
            System.out.println(RED + "❌ Error saving file." + RESET);
        }
    }

    // Input helpers
    private static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print(RED + "Invalid input. Enter a valid number: " + RESET);
            sc.nextLine();
        }
        return sc.nextInt();
    }

    private static double getDoubleInput() {
        while (!sc.hasNextDouble()) {
            System.out.print(RED + "Invalid input. Enter a valid number: " + RESET);
            sc.nextLine();
        }
        return sc.nextDouble();
    }
}


