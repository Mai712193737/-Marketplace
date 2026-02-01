import java.util.Set;

public class SkuBundlePromotion implements Promotion {

    private String sku1;
    private String sku2;
    private String sku3;

    public SkuBundlePromotion(String sku1, String sku2, String sku3) {
        this.sku1 = sku1;
        this.sku2 = sku2;
        this.sku3 = sku3;
    }

    @Override
    public double applyOn(Order order) {
        Set<String> skusInOrder = order.getSkus();

        if (skusInOrder.contains(sku1)
                && skusInOrder.contains(sku2)
                && skusInOrder.contains(sku3)) {
            return order.getTotalPrice() * 0.10;
        }
        return 0;
    }
}