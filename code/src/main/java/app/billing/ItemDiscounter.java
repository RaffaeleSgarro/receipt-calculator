package app.billing;

import app.domain.Item;

public interface ItemDiscounter {
    Discount discount(Item item);
}
