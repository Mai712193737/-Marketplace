package model;

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private int sellerId;

    public Product(int id, String name, String description, double price, int sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
    }

    public Product(String name, String description, double price, int sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getSellerId() { return sellerId; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
}
