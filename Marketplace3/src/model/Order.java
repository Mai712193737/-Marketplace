package model;

import java.time.LocalDateTime;

public class Order {

    private int id;
    private int customerId;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;

    // Constructor كامل (عند القراءة من الداتا بيز)
    public Order(int id,
                 int customerId,
                 double totalAmount,
                 String status,
                 LocalDateTime createdAt) {

        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Constructor عند إنشاء أوردر جديد
    public Order(int customerId, String status) {
        this.customerId = customerId;
        this.totalAmount = 0;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // ================= GETTERS =================

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ================= SETTERS =================

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ================= DEBUG =================

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
