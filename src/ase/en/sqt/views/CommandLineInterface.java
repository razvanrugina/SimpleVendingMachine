package ase.en.sqt.views;

import ase.en.sqt.controllers.ProductController;
import ase.en.sqt.controllers.VendingMachineController;

import java.util.Scanner;

public class CommandLineInterface {
    private static final ProductController productService = new ProductController();
    private static final VendingMachineController vendingMachineService = new VendingMachineController(productService);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nVending Machine CLI:");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Vending Machines");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> productService.manageProducts(scanner);
                case 2 -> vendingMachineService.manageVendingMachines(scanner);
                case 3 -> running = false;
                default -> System.out.println("Invalid choice. Select an option from 1 to 3.");
            }
        }
        scanner.close();
        System.out.println("Exiting CLI.");
    }
}
