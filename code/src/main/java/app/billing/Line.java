package app.billing;

import app.domain.Item;

import java.math.BigDecimal;

public class Line {

    private final Item item;
    private final Discount discount;

    public Line(Item item, Discount discount) {
        this.item = item;
        this.discount = discount;
    }

    public String getDescription() {
        return item.getProduct().getDescription();
    }

    public BigDecimal getQuantity() {
        return item.getQuantity();
    }

    public String getDiscountDescription() {
        return discount.getDescription();
    }

    public BigDecimal getDiscountAmount() {
        return discount.getAmount();
    }
}
