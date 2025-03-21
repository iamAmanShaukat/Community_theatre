package project.community.theatre.service.impl.discountStrategy;

import project.community.theatre.service.DiscountStrategy;

public class ChildDiscountStrategy implements DiscountStrategy {
    private final double discountPercentage;

    public ChildDiscountStrategy(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(double price, int quantity) {
        return price * quantity * (discountPercentage / 100);
    }
}