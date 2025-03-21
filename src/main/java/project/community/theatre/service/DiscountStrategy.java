package project.community.theatre.service;

public interface DiscountStrategy {
    double calculateDiscount(double price, int quantity);
}