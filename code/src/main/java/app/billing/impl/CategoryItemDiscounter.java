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
            case BOOKS: return discount(item, "0.12", "Books -12%");
            case GROCERY: return discount(item, "0.075", "Grocery -7.5%");
            default: return NO_DISCOUNT;
        }
    }

    private Discount discount(Item item, String discount, String description) {
        BigDecimal quantity = item.getQuantity();
        BigDecimal unitPrice = item.getProduct().getUnitPrice();
        BigDecimal subtotal = quantity.multiply(unitPrice);
        BigDecimal amount = subtotal.multiply(new BigDecimal(discount));
        return new Discount(description, amount);
    }
}
