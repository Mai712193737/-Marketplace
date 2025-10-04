public class CarrierManagement {
     private boolean active;
     private String id ;
     private String name;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    private String carrierCode;
 }
 public CarrierManagement( boolean active, String id , String name, String carrierCode){
    this.active=active;
    this.id =id ;
    this.name=name;
    this.carrierCode=carrierCode;
}
