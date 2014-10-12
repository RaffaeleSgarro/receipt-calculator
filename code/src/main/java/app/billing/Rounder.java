package app.billing;

import java.math.BigDecimal;

public interface Rounder {
    public BigDecimal round(BigDecimal val);
}
