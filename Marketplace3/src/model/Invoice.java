package model;

import java.time.LocalDateTime;

public class Invoice {

    private int id;
    private int orderId;
    private LocalDateTime issueDate;
    private double total;

    public Invoice(int id, int orderId, LocalDateTime issueDate, double total) {
        this.id = id;
        this.orderId = orderId;
        this.issueDate = issueDate;
        this.total = total;
    }

    public Invoice(int orderId, double total) {
        this.orderId = orderId;
        this.total = total;
    }

    public int getId() { return id; }
    public int getOrderId() { return orderId; }
    public LocalDateTime getIssueDate() { return issueDate; }
    public double getTotal() { return total; }
}
