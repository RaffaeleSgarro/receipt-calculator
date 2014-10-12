package app.domain;

import java.math.BigDecimal;

public class Product {

    private String description;
    private Category category;
    private BigDecimal unitPrice;

    public Product(Category category, String description, BigDecimal unitPrice) {
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
