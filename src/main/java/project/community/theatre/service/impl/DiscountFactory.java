package project.community.theatre.service.impl;

import project.community.theatre.service.DiscountStrategy;
import project.community.theatre.service.impl.discountStrategy.ChildDiscountStrategy;
import project.community.theatre.service.impl.discountStrategy.LastHourDiscountStrategy;
import project.community.theatre.service.impl.discountStrategy.PensionerDiscountStrategy;
import project.community.theatre.service.impl.discountStrategy.WeekdayDiscountStrategy;

import java.util.Map;

public class DiscountFactory {
    /**
     * Returns a DiscountStrategy implementation based on the given discount type and percentage.
     *
     * @param discountType The type of discount to be applied. Supported types are "CHILDREN",
     *                     "PENSIONERS", "LAST_HOUR", and "WEEKDAY_SPECIAL".
     * @param discountPercentage The percentage of discount to apply.
     * @return A DiscountStrategy instance corresponding to the specified discount type.
     * @throws IllegalArgumentException If the discount type is unknown or unsupported.
     */
    public static DiscountStrategy getDiscountStrategy(String discountType, double discountPercentage) {
        return switch (discountType.toUpperCase()) {
            case "CHILDREN" -> new ChildDiscountStrategy(discountPercentage);
            case "PENSIONERS" -> new PensionerDiscountStrategy(discountPercentage);
            case "LAST_HOUR" -> new LastHourDiscountStrategy(discountPercentage);
            case "WEEKDAY_SPECIAL" -> new WeekdayDiscountStrategy(discountPercentage);
            default -> throw new IllegalArgumentException("Unknown discount type: " + discountType);
        };
    }
}