public class Order {
    private long id ;
    private String customer;
    //lines arraylist
    //status:OrderStatues
    private Double subtotal ;
    private Double discount ;
    private Double shippingFee;
    private Double grandTotal ;
    private void addLine(){}
    private void allocateStock(){}
    private void applyPromotion(){}
    private void recalcTotal(){}
    private void changeStatus(newStatus:OrderStatus){}
    private void cancel(){}
    private void refund(){}

    public Order(long id, String customer, Double subtotal, Double discount, Double shippingFee, Double grandTotal) {
        this.id = id;
        this.customer = customer;
        this.subtotal = subtotal;
        this.discount = discount;
        this.shippingFee = shippingFee;
        this.grandTotal = grandTotal;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
