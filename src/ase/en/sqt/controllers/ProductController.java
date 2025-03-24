package ase.en.sqt.controllers;

import ase.en.sqt.models.Product;
import ase.en.sqt.models.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final List<Product> products = new ArrayList<>();
    private int productIdCounter = 1;

    public void manageProducts(Scanner scanner) {
        System.out.println("Product management:");
        System.out.println("1. Create Product");
        System.out.println("2. List Products");
        System.out.println("3. Delete Product");
        System.out.println("4. List Products with Filter");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> createProduct(scanner);
            case 2 -> listProducts();
            case 3 -> deleteProduct(scanner);
            case 4 -> listProductsWithFilter(scanner);
            default -> System.out.println("Invalid choice.");
        }
    }

    public void createProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        System.out.print("Enter product cost: ");
        if (!scanner.hasNextDouble()) {  // Check if input is a valid number
            System.out.println("Invalid cost. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        double cost = scanner.nextDouble();
        scanner.nextLine();

        if (cost <= 0) {
            System.out.println("Cost must be greater than 0.");
            return;
        }

        System.out.print("Enter product type (HOT, COLD, IDK): ");
        try {
            ProductType type = ProductType.valueOf(scanner.next().toUpperCase());
            Product product = new Product(productIdCounter++, name, cost, type);
            products.add(product);
            System.out.println("Product added: " + product);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid product type. Please enter HOT, COLD, or IDK.");
        }
    }

    public void listProducts() {
        products.forEach(System.out::println);
    }
    public void deleteProduct(Scanner scanner) {
        if (products.isEmpty()) {
            System.out.println("No products available to delete.");
            return;
        }

        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Product productToRemove = getProductById(productId);

        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product ID not found.");
        }
    }


    public void listProductsWithFilter(Scanner scanner) {
        System.out.print("Enter product type to filter (HOT, COLD, IDK, or ALL for all products): ");
        String typeInput = scanner.next().toUpperCase();

        try {
            if (typeInput.equals("ALL")) {
                listProducts(); // Show all products
                return;
            }

            ProductType filterType = ProductType.valueOf(typeInput);

            products.stream()
                    .filter(product -> product.getType() == filterType)
                    .forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid product type. Please enter HOT, COLD, IDK, or ALL.");
        }
    }


    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
