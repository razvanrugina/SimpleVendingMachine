package ase.en.sqt.models;

import ase.en.sqt.models.enums.ProductType;

public class Product {
    private final int id;
    private String name;
    private double cost;
    private ProductType type;

    public Product(int id, String name, double cost, ProductType type) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost must be greater than zero.");
        }

        this.id = id;
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getCost() {
        return cost;
    }
    public ProductType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', cost=" + cost + ", type=" + type + "}";
    }
}
