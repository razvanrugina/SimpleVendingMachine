package ase.en.sqt.models;

import ase.en.sqt.models.enums.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendingMachine {
    private final int id;
    private String name;
    private String location;
    private int capacity;

    private List<Product> hotStuffList = new ArrayList<>();
    private List<Product> coldStuffList = new ArrayList<>();

    public VendingMachine(int id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.hotStuffList = new ArrayList<>();
        this.coldStuffList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public boolean addProduct(Product product) {
        return switch (product.getType()) {
            case HOT -> addToList(hotStuffList, product);
            case COLD -> addToList(coldStuffList, product);
            case IDK -> (addToList(hotStuffList, product) || addToList(coldStuffList, product));
        };
    }

    public boolean addToList(List<Product> list, Product product) {
        if (list.size() < capacity) {
            list.add(product);
            return true;
        }
        return false;
    }

    public boolean moveProduct(int productId, ProductType targetCompartment) {
        Optional<Product> productToMove = findProductById(productId);

        if (productToMove.isPresent()) {
            Product product = productToMove.get();

            List<Product> sourceList = (hotStuffList.contains(product)) ? hotStuffList : coldStuffList;
            List<Product> targetList = (targetCompartment == ProductType.HOT) ? hotStuffList : coldStuffList;

            if (product.getType() != targetCompartment && product.getType() != ProductType.IDK) {
                System.out.println("Invalid move: " + product.getName() + " cannot be stored in " + targetCompartment);
                return false;
            }

            if (targetList.size() < capacity) {
                sourceList.remove(product);
                targetList.add(product);
                System.out.println("Product moved successfully.");
                return true;
            } else {
                System.out.println("Target compartment is full.");
                return false;
            }
        }

        System.out.println("Product not found.");
        return false;
    }

    public Optional<Product> findProductById(int id) {
        return hotStuffList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .or(() -> coldStuffList.stream().filter(p -> p.getId() == id).findFirst());
    }

    @Override
    public String toString() {
        return "VendingMachine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", hotStuffList=" + hotStuffList +
                ", coldStuffList=" + coldStuffList +
                '}';
    }
}
