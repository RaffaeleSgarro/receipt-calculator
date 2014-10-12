package app.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Basket implements Iterable<Item> {

    private final List<Item> items = new ArrayList<>();

    public Basket add(Item item) {
        items.add(item);
        return this;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
