package app.billing;

import app.domain.Basket;
import app.domain.Item;

import java.math.BigDecimal;

public class Calculator {

    private final ItemDiscounter itemDiscounter;
    private final VolumeDiscounter volumeDiscounter;
    private final Rounder rounder;

    public Calculator(ItemDiscounter itemDiscounter, VolumeDiscounter volumeDiscounter, Rounder rounder) {
        this.itemDiscounter = itemDiscounter;
        this.volumeDiscounter = volumeDiscounter;
        this.rounder = rounder;
    }

    public Receipt calculate(Basket basket) {

        Receipt receipt = new Receipt();

        BigDecimal grossTotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (Item item : basket) {
            Discount discount = itemDiscounter.discount(item);
            BigDecimal quantity = item.getQuantity();
            BigDecimal unitPrice = item.getProduct().getUnitPrice();
            BigDecimal beforeDiscount = quantity.multiply(unitPrice);
            grossTotal = grossTotal.add(beforeDiscount.subtract(discount.getAmount()));
            totalDiscount = totalDiscount.add(discount.getAmount());
            receipt.add(item, discount);
        }

        receipt.setGrossTotal(grossTotal);

        Discount volumeDiscount = volumeDiscounter.discount(receipt);
        receipt.setTotalDiscount(totalDiscount);

        BigDecimal netTotal = rounder.round(grossTotal.subtract(volumeDiscount.getAmount()));
        receipt.setNetTotal(netTotal);

        return receipt;
    }
}
