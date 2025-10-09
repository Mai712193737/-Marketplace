import java.util.ArrayList;

public class Order {
    private long id ;
    private String customer;
    private Double subtotal ;
    private Double discount ;
    private Double shippingFee;
    private Double grandTotal ;
    private void addLine(){}
    private void allocateStock(){}
    private void applyPromotion(){}
    private void recalcTotal(){}
    private void cancel(){}
    private void refund(){}
    public OrderStatus status;

    public enum OrderStatus {
        NEW,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }


    private void changeStatus(OrderStatus newStatus) {
        this.status = newStatus;
        System.out.println("✅ تم تغيير حالة الطلب إلى: " + newStatus);
    }
/***********************************/
    public Order(long id, String customer, Double subtotal, Double discount, Double shippingFee, Double grandTotal ,OrderStatus status ) {
        this.id = id;
        this.customer = customer;
        this.subtotal = subtotal;
        this.discount = discount;
        this.shippingFee = shippingFee;
        this.grandTotal = grandTotal;
        this.status=status;
    }
/********************************************/
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

public OrderStatus getStatus() {
    return status;
}

public void setStatus(OrderStatus status) {
    this.status = status;
}
}