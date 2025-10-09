public class AddEvent {
   private String id;
   private Long orderId;
   private String actor;
   private String  action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetailes() {
        return detailes;
    }

    public void setDetailes(String detailes) {
        this.detailes = detailes;
    }

    public Long getTimestamoMillis() {
        return timestamoMillis;
    }

    public void setTimestamoMillis(Long timestamoMillis) {
        this.timestamoMillis = timestamoMillis;
    }

    private String detailes;

    public AddEvent(String id, Long orderId, String actor, String action, String detailes, Long timestamoMillis) {
        this.id = id;
        this.orderId = orderId;
        this.actor = actor;
        this.action = action;
        this.detailes = detailes;
        this.timestamoMillis = timestamoMillis;
    }

    private Long timestamoMillis;
}
