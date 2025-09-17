public class Product {
    private String sku;
    private int qty;
    private String title;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvalabQtr() {
        return avalabQtr;
    }

    public void setAvalabQtr(int avalabQtr) {
        this.avalabQtr = avalabQtr;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Product(String sku, int qty, String title, int avalabQtr, Double price, boolean active) {
        this.sku = sku;
        this.qty = qty;
        this.title = title;
        this.avalabQtr = avalabQtr;
        this.price = price;
        this.active = active;
    }

    private int avalabQtr;
    private Double price;
    private boolean active;
}
