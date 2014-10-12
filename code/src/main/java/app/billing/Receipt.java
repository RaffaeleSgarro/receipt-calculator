package app.billing;

import app.domain.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Receipt implements Iterable<Line> {

    private final List<Line> lines = new ArrayList<>();

    private BigDecimal grossTotal;
    private BigDecimal totalDiscount;
    private BigDecimal netTotal;

    @Override
    public Iterator<Line> iterator() {
        return lines.iterator();
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void add(Item item, Discount discount) {
        lines.add(new Line(item, discount));
    }
}
