package app.billing;

import app.billing.impl.CategoryItemDiscounter;
import app.billing.impl.FiveCentRounder;
import app.billing.impl.GrossTotalDiscounter;
import app.domain.Basket;
import app.domain.Category;
import app.domain.Item;
import app.domain.Product;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static app.domain.Category.BOOKS;
import static app.domain.Category.GROCERY;
import static app.domain.Category.OTHER;

import static org.testng.Assert.*;

public class EndToEndTest {

    private Calculator target;
    private ItemDiscounter itemDiscounter;
    private VolumeDiscounter volumeDiscounter;
    private Rounder rounder;
    private Basket basket;
    private Receipt receipt;

    @BeforeMethod
    public void setUp() throws Exception {
        itemDiscounter = new CategoryItemDiscounter();
        volumeDiscounter = new GrossTotalDiscounter("40", "0.05");
        rounder = new FiveCentRounder();
        target = new Calculator(itemDiscounter, volumeDiscounter, rounder);
        basket = new Basket();
    }

    @Test
    public void testBasket1() throws Exception {
        add(GROCERY, "Pasta 1kg", $("4.29"));
        add(BOOKS, "Book", $("10.12"));
        calculate();
        totalShouldBe("12.90");
        discountShouldBe("1.54");
    }

    @Test
    public void testBasket2() throws Exception {
        add(GROCERY, "Coffee 500g", $("3.21"));
        add(GROCERY, "Pasta 1kg", $("4.29"));
        add(OTHER, "Cake", $("2.35"));
        calculate();
        totalShouldBe("9.30");
        discountShouldBe("0.56");
    }

    @Test
    public void testBasket3() throws Exception {
        add(OTHER, "Chocolate", $("2.10"), x("10"));
        add(OTHER, "Wine", $("10.5"));
        add(BOOKS, "Book", $("15.05"));
        add(OTHER, "Apple", $("0.5"), x("5"));
        calculate();
        totalShouldBe("44.90");
        discountShouldBe("4.17");
    }

    private void calculate() {
        receipt = target.calculate(basket);
    }

    private static class Quantity {

        private final BigDecimal val;

        private Quantity(String val) {
            this.val = new BigDecimal(val);
        }

        public BigDecimal val() {
            return val;
        }
    }

    private static class Price {

        private final BigDecimal val;

        private Price(String val) {
            this.val = new BigDecimal(val);
        }

        public BigDecimal val() {
            return val;
        }
    }

    private Quantity x(String str) {
        return new Quantity(str);
    }

    private Price $(String str){
        return new Price(str);
    }

    private void totalShouldBe(String expected) {
        assertEquals(receipt.getNetTotal(), new BigDecimal(expected));
    }

    private void discountShouldBe(String expected) {
        BigDecimal expectedBigDecimal = new BigDecimal(expected);
        if (expectedBigDecimal.scale() != 2) throw new IllegalArgumentException("Assertions needs 2 digits precision. Given " + expected);
        assertEquals(receipt.getTotalDiscount().setScale(2, BigDecimal.ROUND_HALF_UP), expectedBigDecimal);
    }

    private void add(Category category, String description, Price unitPrice) {
        basket.add(new Item(new Product(category, description, unitPrice.val()), new Quantity("1").val()));
    }

    private void add(Category category, String description, Price unitPrice, Quantity quantity) {
        basket.add(new Item(new Product(category, description, unitPrice.val()), quantity.val()));
    }

}