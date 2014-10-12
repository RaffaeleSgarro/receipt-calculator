package app.billing.impl;

import app.billing.Discount;
import app.billing.Receipt;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class GrossTotalDiscounterTest {

    private GrossTotalDiscounter target;
    private Receipt receipt;

    @BeforeMethod
    public void setUp() throws Exception {
        target = new GrossTotalDiscounter("40", "0.05");
        receipt = new Receipt();
    }

    @Test
    public void testDiscount() throws Exception {
        receipt.setGrossTotal(new BigDecimal("100"));
        Discount discount = target.discount(receipt);
        assertEquals(discount.getAmount().setScale(2, BigDecimal.ROUND_DOWN), new BigDecimal("5.00"));
    }
}