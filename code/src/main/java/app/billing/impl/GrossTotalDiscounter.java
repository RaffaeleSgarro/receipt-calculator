package app.billing.impl;

import app.billing.Discount;
import app.billing.Receipt;
import app.billing.VolumeDiscounter;

import java.math.BigDecimal;

public class GrossTotalDiscounter implements VolumeDiscounter {

    private static final Discount NO_DISCOUNT = new Discount("0%", BigDecimal.ZERO);

    @Override
    public Discount discount(Receipt receipt) {
        return NO_DISCOUNT;
    }
}
