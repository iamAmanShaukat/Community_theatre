package project.community.theatre.service.impl.discountStrategy;

import project.community.theatre.service.DiscountStrategy;

public class LastHourDiscountStrategy implements DiscountStrategy {
    private final double discountPercentage;

    public LastHourDiscountStrategy(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return price * quantity * (discountPercentage / 100);
    }
}