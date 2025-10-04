public class OrderLine {
    public String sku;
    private int qty;
    private Double unitPrice;
    private Double LineTotal;

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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(Double lineTotal) {
        LineTotal = lineTotal;
    }

    public OrderLine(String sku, int qty, Double unitPrice, Double lineTotal) {
        this.sku = sku;
        this.qty = qty;
        this.unitPrice = unitPrice;
        LineTotal = lineTotal;
    }
}
