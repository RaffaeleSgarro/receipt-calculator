package app.billing.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class FiveCentRounderTest {

    FiveCentRounder target;

    @BeforeMethod
    public void setUp() throws Exception {
        target = new FiveCentRounder();
    }

    @Test
    public void testRound() throws Exception {
        test("12.87385", "12.90");
    }

    private void test(String in, String out) {
        Assert.assertEquals(target.round($(in)), $(out));
    }

    private BigDecimal $(String str) {
        return new BigDecimal(str);
    }
}