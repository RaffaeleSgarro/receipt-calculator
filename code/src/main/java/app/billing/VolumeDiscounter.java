package app.billing;

public interface VolumeDiscounter {
    Discount discount(Receipt basket);
}
