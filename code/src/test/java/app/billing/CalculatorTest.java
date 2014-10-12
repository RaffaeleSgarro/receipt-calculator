package app.billing;

import app.domain.Basket;
import app.domain.Category;
import app.domain.Item;
import app.domain.Product;
import org.mockito.InOrder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CalculatorTest {

    private Calculator target;
    private ItemDiscounter itemDiscounter;
    private Rounder rounder;
    private VolumeDiscounter volumeDiscounter;
    private Basket basket;

    @BeforeMethod
    public void setUp() throws Exception {

        Discount noDiscount = new Discount("Test discount 0%", BigDecimal.ZERO);

        itemDiscounter = mock(ItemDiscounter.class);
        when(itemDiscounter.discount(any(Item.class))).thenReturn(noDiscount);

        volumeDiscounter = mock(VolumeDiscounter.class);
        when(volumeDiscounter.discount(any(Receipt.class))).thenReturn(noDiscount);

        rounder = mock(Rounder.class);
        when(rounder.round(any(BigDecimal.class))).thenAnswer(returnsFirstArg());

        target = new Calculator(itemDiscounter, volumeDiscounter, rounder);
        basket = new Basket();
    }

    @Test
    public void testCalculate() throws Exception {

        InOrder inOrder = inOrder(itemDiscounter, volumeDiscounter, rounder);

        Item harryPotter = new Item(new Product(Category.BOOKS, "Harry Potter", new BigDecimal("9.99")), new BigDecimal("1"));
        basket.add(harryPotter);

        Item cake = new Item(new Product(Category.OTHER, "Cheescake", new BigDecimal("6.50")), new BigDecimal("1"));
        basket.add(cake);

        target.calculate(basket);

        inOrder.verify(itemDiscounter).discount(harryPotter);
        inOrder.verify(itemDiscounter).discount(cake);
        inOrder.verify(volumeDiscounter).discount(any(Receipt.class));
        inOrder.verify(rounder).round(any(BigDecimal.class));
    }
}