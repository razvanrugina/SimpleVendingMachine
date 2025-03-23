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
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> createProduct(scanner);
            case 2 -> listProducts();
            default -> System.out.println("Invalid choice.");
        }
    }

    public void createProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product cost: ");
        double cost = scanner.nextDouble();
        System.out.print("Enter product type (HOT, COLD, IDK): ");
        ProductType type = ProductType.valueOf(scanner.next().toUpperCase());

        Product product = new Product(productIdCounter++, name, cost, type);
        products.add(product);
        System.out.println("Product added: " + product);

    }

    public void listProducts() {
        products.forEach(System.out::println);
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
