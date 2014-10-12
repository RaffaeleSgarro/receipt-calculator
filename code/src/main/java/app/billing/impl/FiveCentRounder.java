package app.billing.impl;

import app.billing.Rounder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FiveCentRounder implements Rounder {

    @Override
    public BigDecimal round(BigDecimal val) {
        val = val.setScale(2, RoundingMode.HALF_UP);
        while (!endsWithZeroOrFive(val)) {
            val = val.add(new BigDecimal("0.01"));
        }
        return val;
    }

    private boolean endsWithZeroOrFive(BigDecimal val) {
        return val.toString().matches(".*[0,5]$");
    }

}
