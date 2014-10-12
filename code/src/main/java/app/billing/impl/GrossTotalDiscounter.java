package app.billing.impl;

import app.billing.Discount;
import app.billing.Receipt;
import app.billing.VolumeDiscounter;

import java.math.BigDecimal;

public class GrossTotalDiscounter implements VolumeDiscounter {

    private static final Discount NO_DISCOUNT = new Discount("0%", BigDecimal.ZERO);

    private final BigDecimal threshold;
    private final BigDecimal discountCoefficient;

    public GrossTotalDiscounter(String threshold, String discountCoefficient) {
        this(new BigDecimal(threshold), new BigDecimal(discountCoefficient));
    }

    public GrossTotalDiscounter(BigDecimal threshold, BigDecimal discountCoefficient) {
        this.threshold = threshold;
        this.discountCoefficient = discountCoefficient;
    }

    @Override
    public Discount discount(Receipt receipt) {

        return (receipt.getGrossTotal().compareTo(threshold) > 0 )
                ? discountGrossTotal(receipt.getGrossTotal())
                : NO_DISCOUNT;
    }

    private Discount discountGrossTotal(BigDecimal grossTotal) {
        return new Discount("5% on gross total greater than 40 euros", grossTotal.multiply(discountCoefficient));
    }
}
