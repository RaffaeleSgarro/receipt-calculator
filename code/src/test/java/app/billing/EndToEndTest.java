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
        _("Pasta 1kg", GROCERY, "4.29");
        _("Book", BOOKS, "10.12");
        calculate();
        totalShouldBe("12.90");
        discountShouldBe("1.54");
    }

    @Test
    public void testBasket2() throws Exception {
        _("Coffee 500g", GROCERY, "3.21");
        _("Pasta 1kg", GROCERY, "4.29");
        _("Cake", OTHER, "2.35");
        calculate();
        totalShouldBe("9.30");
        discountShouldBe("0.56");
    }

    @Test
    public void testBasket3() throws Exception {
        _("Chocolate", OTHER, "2.10", "10");
        _("Wine", OTHER, "10.5");
        _("Book", BOOKS, "15.05");
        _("Apple", OTHER, "0.5", "5");
        calculate();
        totalShouldBe("44.90");
        discountShouldBe("4.17");
    }

    private void calculate() {
        receipt = target.calculate(basket);
    }

    private void totalShouldBe(String expected) {
        assertEquals(receipt.getNetTotal(), new BigDecimal(expected));
    }

    private void discountShouldBe(String expected) {
        BigDecimal expectedBigDecimal = new BigDecimal(expected);
        if (expectedBigDecimal.scale() != 2) throw new IllegalArgumentException("Assertions needs 2 digits precision. Given " + expected);
        assertEquals(receipt.getTotalDiscount().setScale(2, BigDecimal.ROUND_HALF_UP), expectedBigDecimal);
    }

    private void _(String description, Category category, String unitPrice) {
        basket.add(new Item(new Product(category, description, new BigDecimal(unitPrice)), BigDecimal.ONE));
    }

    private void _(String description, Category category, String unitPrice, String quantity) {
        basket.add(new Item(new Product(category, description, new BigDecimal(unitPrice)), new BigDecimal(quantity)));
    }

}