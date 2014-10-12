package app.billing.impl;

import app.billing.Discount;
import app.billing.ItemDiscounter;
import app.domain.Item;

import java.math.BigDecimal;

public class CategoryItemDiscounter implements ItemDiscounter {

    private static final Discount NO_DISCOUNT = new Discount("", BigDecimal.ZERO);

    @Override
    public Discount discount(Item item) {
        switch (item.getProduct().getCategory()) {
            case BOOKS: return new Discount("Books -12%", discount(item, "0.12"));
            case GROCERY: return new Discount("Grocery -7.5%", discount(item, "0.075"));
            default: return NO_DISCOUNT;
        }
    }

    private BigDecimal discount(Item item, String discount) {
        BigDecimal quantity = item.getQuantity();
        BigDecimal unitPrice = item.getProduct().getUnitPrice();
        BigDecimal subtotal = quantity.multiply(unitPrice);
        return subtotal.multiply(new BigDecimal(discount));
    }
}
