public class Shipment {


    private Long trackingNumber;
    private String carrierCode;
    private Long orderId;
    private ShipmentStatus status;


    public enum ShipmentStatus {
        CREATED,
        LABEL_PRINTED,
        IN_TRANSIT,
        DELIVERED,
        LOST
    }


    public Shipment(String carrierCode, Long orderId) {
        this.trackingNumber = generateTrackingNumber();
        this.carrierCode = carrierCode;
        this.orderId = orderId;
        this.status = ShipmentStatus.CREATED;
    }


    public Long generateTrackingNumber() {
        return System.currentTimeMillis();
    }


    public void updateStatus(ShipmentStatus newStatus) {
        this.status = newStatus;
    }


    public Long getTrackingNumber() {
        return trackingNumber;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public ShipmentStatus getStatus() {
        return status;
    }


    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}