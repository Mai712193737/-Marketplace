import java.util.ArrayList;
import java.util.List;

public class Order {
    protected Long id;
    protected String customerID;
    protected ArrayList<OrderLine> lines;
    private OrderStatus status;
    private Double subtotal;
    private Double discount;
    private Double shippingFee;
    private Double grandTotal;

    public Order(Long id, String customerID, ArrayList<OrderLine> lines, OrderStatus status, Double subtotal, Double discount, Double shippingFee, Double grandTotal) {
        this.id = id;
        this.customerID = customerID;
        this.lines = lines;
        this.status = status;
        this.subtotal = subtotal;
        this.discount = discount;
        this.shippingFee = shippingFee;
        this.grandTotal = grandTotal;
    }

    public void addLine(OrderLine line) {
        if (line == null) return;

        if (this.lines == null) {
            this.lines = new ArrayList<>();
        }

        this.lines.add(line);
        recalcTotal();
    }


    public void allocateStock() {
        if (lines == null) return;

        for (OrderLine line : lines) {
            line.allocateStock();
        }
    }

    public void applyPromotions(List<Promotion> promos) {
        if (promos == null || promos.isEmpty()) return;

        double totalDiscount = 0.0;

        for (Promotion promo : promos) {
            totalDiscount += promo.apply(this);
        }

        this.discount = totalDiscount;
        recalcTotal();
    }


    public void recalcTotal() {
        double newSubtotal = 0.0;

        if (lines != null) {
            for (OrderLine line : lines) {
                newSubtotal += line.getLineTotal();
            }
        }

        this.subtotal = newSubtotal;

        if (this.discount == null) {
            this.discount = 0.0;
        }

        if (this.shippingFee == null) {
            this.shippingFee = 0.0;
        }

        this.grandTotal = subtotal - discount + shippingFee;
    }


    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) return;

        this.status = newStatus;
    }


    public void cancel() {
        if (this.status == OrderStatus.SHIPPED ||
                this.status == OrderStatus.REFUNDED) {
            throw new IllegalStateException("Cannot cancel this order.");
        }

        this.status = OrderStatus.CANCELLED;
    }


    public void refund() {
        if (this.status != OrderStatus.PAID &&
                this.status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Refund not allowed.");
        }

        this.status = OrderStatus.REFUNDED;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<OrderLine> lines) {
        this.lines = lines;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
