package model;

import model.enums.ShipmentStatus;

public class Shipment {
  Long TrackingNumber;

  public Shipment(Long trackingNumber2, String carrierCode, Long orderId, ShipmentStatus status) {
    // TODO Auto-generated constructor stub
  }

  public Long getTrackingNumber() {
    return TrackingNumber;
  }

  public void setTrackingNumber(Long trackingNumber) {
    TrackingNumber = trackingNumber;
  }

  public Long getCarrierCode() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCarrierCode'");
  }

  public Long getOrderId() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOrderId'");
  }

  public Long getStatus() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
  }

}
