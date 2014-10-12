package app.domain;

import java.math.BigDecimal;

public class Item {

    private Product product;
    private BigDecimal quantity;

    public Item(Product product, BigDecimal quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
