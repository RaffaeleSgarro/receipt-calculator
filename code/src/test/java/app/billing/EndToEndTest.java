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
        add(GROCERY, "Pasta 1kg", at("4.29"));
        add(BOOKS, "Book", at("10.12"));
        calculate();
        totalShouldBe("12.90");
        discountShouldBe("1.54");
    }

    @Test
    public void testBasket2() throws Exception {
        add(GROCERY, "Coffee 500g", at("3.21"));
        add(GROCERY, "Pasta 1kg", at("4.29"));
        add(OTHER, "Cake", at("2.35"));
        calculate();
        totalShouldBe("9.30");
        discountShouldBe("0.56");
    }

    @Test
    public void testBasket3() throws Exception {
        add(OTHER, "Chocolate", at("2.10"), quantity("10"));
        add(OTHER, "Wine", at("10.5"));
        add(BOOKS, "Book", at("15.05"));
        add(OTHER, "Apple", at("0.5"), quantity("5"));
        calculate();
        totalShouldBe("44.90");
        discountShouldBe("4.17");
    }

    private void calculate() {
        receipt = target.calculate(basket);
    }

    private BigDecimal quantity(String str) {
        return new BigDecimal(str);
    }

    private BigDecimal at(String str){
        return new BigDecimal(str);
    }

    private void totalShouldBe(String expected) {
        assertEquals(receipt.getNetTotal(), new BigDecimal(expected));
    }

    private void discountShouldBe(String expected) {
        BigDecimal expectedBigDecimal = new BigDecimal(expected);
        if (expectedBigDecimal.scale() != 2) throw new IllegalArgumentException("Assertions needs 2 digits precision. Given " + expected);
        assertEquals(receipt.getTotalDiscount().setScale(2, BigDecimal.ROUND_HALF_UP), expectedBigDecimal);
    }

    private void add(Category category, String description, BigDecimal unitPrice) {
        basket.add(new Item(new Product(category, description, unitPrice), BigDecimal.ONE));
    }

    private void add(Category category, String description, BigDecimal unitPrice, BigDecimal quantity) {
        basket.add(new Item(new Product(category, description, unitPrice), quantity));
    }

}