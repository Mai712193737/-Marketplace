public class ThresholdFixedDiscountPromotion implements Promotion {

    private double threshold;
    private double discount;

    public ThresholdFixedDiscountPromotion(double threshold, double discount) {
        this.threshold = threshold;
        this.discount = discount;
    }

    @Override
    public double applyOn(Order order) {
        if (order.getTotalPrice() >= threshold) {
            return discount;
        }
        return 0;
    }
}