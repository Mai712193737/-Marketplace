package model;

public class Shipment {

    private int id;
    private int orderId;
    private String status;
    private String trackingNumber;

    public Shipment(int id, int orderId, String status, String trackingNumber) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.trackingNumber = trackingNumber;
    }

    public Shipment(int orderId, String status, String trackingNumber) {
        this.orderId = orderId;
        this.status = status;
        this.trackingNumber = trackingNumber;
    }

    public int getId() { return id; }
    public int getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public String getTrackingNumber() { return trackingNumber; }

    public void setStatus(String status) { this.status = status; }
}
