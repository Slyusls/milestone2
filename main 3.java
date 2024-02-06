import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SalableProduct {
    private String name;
    private String description;
    private double price;
    private int quantity;

    public SalableProduct(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}

class ShoppingCart {
    private List<SalableProduct> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(SalableProduct product) {
        items.add(product);
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        for (SalableProduct item : items) {
            System.out.println("- " + item);
        }
        System.out.println("Total Price: $" + calculateTotalPrice());
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (SalableProduct item : items) {
            total += item.getPrice();
        }
        return total;
    }
}

class InventoryManager {
    private List<SalableProduct> inventory;

    public InventoryManager() {
        this.inventory = new ArrayList<>();
    }

    public void populateInventory() {
        // Hard-code initial inventory
        inventory.add(new SalableProduct("Small sword", "Red magic sword", 50.0, 10));
        inventory.add(new SalableProduct("Weak bow", "HUGE BOW", 40.0, 15));
        inventory.add(new SalableProduct("copper plate", "RUBY ARMOR", 100.0, 5));
        inventory.add(new SalableProduct("Health Potion", "HUGE POTION", 10.0, 30));
    }

    public SalableProduct getProductByName(String productName) {
        for (SalableProduct product : inventory) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    public void purchaseProduct(String productName, int quantity, ShoppingCart cart) {
        SalableProduct product = getProductByName(productName);
        if (product != null && product.getQuantity() >= quantity) {
            cart.addItem(new SalableProduct(product.getName(), product.getDescription(), product.getPrice(), quantity));
            product.decreaseQuantity(quantity);
            System.out.println("Product added to the cart: " + product.getName());
        } else {
            System.out.println("Product not available in the specified quantity.");
        }
    }

    public void cancelPurchase(String productName, int quantity, ShoppingCart cart) {
        SalableProduct product = getProductByName(productName);
        if (product != null) {
            cart.addItem(new SalableProduct(product.getName(), product.getDescription(), product.getPrice(), -quantity));
            product.decreaseQuantity(-quantity);
            System.out.println("Purchase canceled for: " + product.getName());
        } else {
            System.out.println("Product not found in the cart.");
        }
    }
}

class StoreFrontApplication {
    private String storeName;
    private InventoryManager inventoryManager;
    private ShoppingCart shoppingCart;

    public StoreFrontApplication(String storeName) {
        this.storeName = storeName;
        this.inventoryManager = new InventoryManager();
        this.shoppingCart = new ShoppingCart();
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to " + storeName + "!");
    }

    public void displayActions() {
        System.out.println("Available Actions:");
        System.out.println("1. Purchase a product");
        System.out.println("2. Cancel a purchase");
        System.out.println("3. Display Shopping Cart");
        System.out.println("4. Exit");
    }

    public void executeAction(int action) {
        Scanner scanner = new Scanner(System.in);
        switch (action) {
            case 1:
                System.out.println("Enter the name of the product to purchase:");
                String purchaseProductName = scanner.nextLine();
                System.out.println("Enter the quantity:");
                try {
                    int purchaseQuantity = Integer.parseInt(scanner.nextLine());
                    inventoryManager.purchaseProduct(purchaseProductName, purchaseQuantity, shoppingCart);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                }
                break;
            case 2:
                System.out.println("Enter the name of the product to cancel:");
                String cancelProductName = scanner.nextLine();
                System.out.println("Enter the quantity to cancel:");
                try {
                    int cancelQuantity = Integer.parseInt(scanner.nextLine());
                    inventoryManager.cancelPurchase(cancelProductName, cancelQuantity, shoppingCart);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                }
                break;
            case 3:
                shoppingCart.displayCart();
                break;
            case 4:
                System.out.println("Exiting the application. Thank you!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    public static void main(String[] args) {
        StoreFrontApplication storeApp = new StoreFrontApplication("Mustafas game");

        // Populate initial inventory
        storeApp.inventoryManager.populateInventory();

        // Display welcome message and available actions
        storeApp.displayWelcomeMessage();

        while (true) {
            storeApp.displayActions();

            // Get user input and execute action
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of the action you want to perform:");
            try {
                int userAction = Integer.parseInt(scanner.nextLine());
                storeApp.executeAction(userAction);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid action number.");
            }
        }
    }
}
