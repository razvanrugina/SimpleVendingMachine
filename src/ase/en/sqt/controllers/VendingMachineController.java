package ase.en.sqt.controllers;

import ase.en.sqt.models.Product;
import ase.en.sqt.models.VendingMachine;
import ase.en.sqt.models.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VendingMachineController {
    private final List<VendingMachine> vendingMachines = new ArrayList<>();
    private final ProductController productController;
    private int machineIdCounter = 1;

    public VendingMachineController(ProductController productController) {
        this.productController = productController;
    }

    public void manageVendingMachines(Scanner scanner) {
        while (true) {
            System.out.println("\nVending Machine management:");
            System.out.println("1. Create Vending Machine");
            System.out.println("2. List Vending Machines");
            System.out.println("3. Add Product to Vending Machine");
            System.out.println("4. Move Product");
            System.out.println("5. Delete Vending Machine");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createVendingMachine(scanner);
                case 2 -> listVendingMachines();
                case 3 -> addProduct(scanner);
                case 4 -> moveProduct(scanner);
                case 5 -> deleteVendingMachine(scanner);
                case 6 -> {
                    System.out.println("Exiting Vending Machine Management.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void createVendingMachine(Scanner scanner) {
        System.out.print("Enter vending machine name: ");
        String name = scanner.nextLine();
        System.out.print("Enter vending machine location: ");
        String location = scanner.nextLine();
        System.out.print("Enter vending machine capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        VendingMachine machine = new VendingMachine(machineIdCounter++, name, location, capacity);
        vendingMachines.add(machine);
        System.out.println("Vending Machine added: " + machine);
    }

    public void listVendingMachines() {
        if (vendingMachines.isEmpty()) {
            System.out.println("No vending machines available.");
            return;
        }
        vendingMachines.forEach(System.out::println);
    }

    public void deleteVendingMachine(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No vending machines available to delete.");
            return;
        }

        listVendingMachines();

        System.out.print("Enter vending machine ID to delete: ");
        int machineId = scanner.nextInt();
        scanner.nextLine();

        Optional<VendingMachine> machineToRemove = vendingMachines.stream()
                .filter(vm -> vm.getId() == machineId)
                .findFirst();

        if (machineToRemove.isPresent()) {
            System.out.print("Are you sure you want to delete vending machine "
                    + machineToRemove.get().getName() + "? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes")) {
                vendingMachines.remove(machineToRemove.get());
                System.out.println("Vending machine deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Vending machine ID not found.");
        }
    }


    public void addProduct(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No vending machines available.");
            return;
        }

        // Display the list of all products
        productController.listProducts();

        System.out.print("Enter product ID to add: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        // Find the product by ID
        Product product = productController.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        // Display vending machine options
        System.out.print("Enter vending machine ID: ");
        int machineId = scanner.nextInt();
        scanner.nextLine();

        Optional<VendingMachine> vendingMachineOpt = vendingMachines.stream()
                .filter(vm -> vm.getId() == machineId)
                .findFirst();

        if (vendingMachineOpt.isEmpty()) {
            System.out.println("Vending Machine not found.");
            return;
        }

        VendingMachine vendingMachine = vendingMachineOpt.get();

        // **Check if the product is already in the vending machine**
        boolean productExists = vendingMachine.getAllProducts().stream()
                .anyMatch(p -> p.getId() == productId);

        if (productExists) {
            System.out.println("This product is already in the vending machine.");
            return;
        }

        // Add the selected product to the vending machine
        if (vendingMachine.addProduct(product)) {
            System.out.println("Product added successfully: " + product);
        } else {
            System.out.println("Failed to add product. Compartment may be full or incorrect.");
        }
    }


    public void moveProduct(Scanner scanner) {
        if (vendingMachines.isEmpty()) {
            System.out.println("No vending machines available.");
            return;
        }

        System.out.print("Enter vending machine ID: ");
        int machineId = scanner.nextInt();
        scanner.nextLine();

        Optional<VendingMachine> vendingMachineOpt = vendingMachines.stream()
                .filter(vm -> vm.getId() == machineId)
                .findFirst();

        if (vendingMachineOpt.isEmpty()) {
            System.out.println("Vending Machine not found.");
            return;
        }

        VendingMachine vendingMachine = vendingMachineOpt.get();

        System.out.print("Enter product ID to move: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter target compartment (HOT or COLD): ");
        String targetTypeStr = scanner.next().toUpperCase();

        try {
            ProductType targetType = ProductType.valueOf(targetTypeStr);
            if (vendingMachine.moveProduct(productId, targetType)) {
                System.out.println("Product moved successfully.");
            } else {
                System.out.println("Move failed. Invalid compartment or full storage.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid compartment type.");
        }
    }
}
