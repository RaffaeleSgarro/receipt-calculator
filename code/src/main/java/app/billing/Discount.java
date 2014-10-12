package app.billing;

import java.math.BigDecimal;

public class Discount {

    private final String description;
    private final BigDecimal amount;

    public Discount(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
