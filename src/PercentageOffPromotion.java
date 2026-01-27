public class PercentageOffPromotion implements Promotion {

    private double percent;

    public PercentageOffPromotion(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyOn(Order order) {
        return order.getTotalPrice() * (percent / 100);
    }

    public double applyOn(OrderLine line) {
        return line.getPrice() * line.getQuantity() * (percent / 100);
    }
}