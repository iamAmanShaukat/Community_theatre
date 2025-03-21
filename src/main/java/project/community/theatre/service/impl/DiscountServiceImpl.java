package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.TicketType;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.exception.DiscountNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.repository.DiscountRepository;
import project.community.theatre.service.DiscountService;
import project.community.theatre.service.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final BandRepository bandRepository;
    private final DiscountRepository discountRepository;

    @Override
    public DiscountResponse calculateDiscount(DiscountRequest request) {
        log.info("Calculating discount for request: {}", request);

        DiscountResponse response = new DiscountResponse();
        List<BandEntity> bands = bandRepository.findAll();
        List<DiscountEntity> discounts = getAllDiscounts();

        Map<String, Double> discountMap = createDiscountMap(discounts);
        PriceBreakdown prices = calculatePrices(request.getBands(), bands);

        if (request.isSocialClub()) {
            response.setSocialClub(calculateSocialClubDiscount(
                    prices.totalFullPrice,
                    request.getTotalTickets(),
                    discountMap
            ));
            double totalReduction = response.getSocialClub();
            response.setReduction(totalReduction);
            response.setFinalPrice(prices.totalFullPrice - totalReduction);
            log.info("Social club discount applied: {}", response.getSocialClub());
            return response;
        }

        applyRegularDiscounts(request, prices, discountMap, response);

        double totalReduction = response.getChild() + response.getPensioner() +
                response.getLastHour() + response.getWeekday();
        response.setReduction(totalReduction);
        response.setFinalPrice(prices.totalFullPrice - totalReduction);

        log.info("Regular discounts applied: {}", response);
        return response;
    }

    private void applyRegularDiscounts(DiscountRequest request, PriceBreakdown prices,
                                       Map<String, Double> discountMap, DiscountResponse response) {
        // Apply Child Discount
        DiscountStrategy childStrategy = DiscountFactory.getDiscountStrategy("CHILDREN",
                discountMap.getOrDefault("CHILDREN", 0.0));
        response.setChild(childStrategy.calculateDiscount(prices.totalChildPrice, 1));

        // Apply Pensioner Discount
        DiscountStrategy pensionerStrategy = DiscountFactory.getDiscountStrategy("PENSIONERS",
                discountMap.getOrDefault("PENSIONERS", 0.0));
        response.setPensioner(pensionerStrategy.calculateDiscount(prices.totalPensionerPrice, 1));

        // Apply Last Hour Discount
        if (isLastHour(request.getShowTime())) {
            DiscountStrategy lastHourStrategy = DiscountFactory.getDiscountStrategy("LAST_HOUR",
                    discountMap.getOrDefault("LAST_HOUR", 0.0));
            response.setLastHour(lastHourStrategy.calculateDiscount(prices.totalFullPrice, 1));
        }

        // Apply Weekday Discount
        if (isWeekday(request.getDay())) {
            DiscountStrategy weekdayStrategy = DiscountFactory.getDiscountStrategy("WEEKDAY_SPECIAL",
                    discountMap.getOrDefault("WEEKDAY_SPECIAL", 0.0));
            response.setWeekday(weekdayStrategy.calculateDiscount(prices.totalFullPrice, 1));
        }
    }

    private boolean isLastHour(LocalDateTime showTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursUntilShow = java.time.temporal.ChronoUnit.HOURS.between(currentTime, showTime);
        return hoursUntilShow <= 1;
    }

    private boolean isWeekday(String day) {
        List<String> weekdays = List.of("monday", "tuesday", "wednesday", "thursday");
        return weekdays.contains(day.toLowerCase());
    }

    private Map<String, Double> createDiscountMap(List<DiscountEntity> discounts) {
        return discounts.stream()
                .collect(Collectors.toMap(
                        DiscountEntity::getDiscountType,
                        DiscountEntity::getDiscountPercentage
                ));
    }

    private PriceBreakdown calculatePrices(Map<String, TicketType> bands, List<BandEntity> bandEntities) {
        log.info("Calculating prices for bands: {}", bands);
        Map<String, Double> bandPrices = bandEntities.stream()
                .collect(Collectors.toMap(BandEntity::getBandId, BandEntity::getPrice));

        double totalChildPrice = 0;
        double totalPensionerPrice = 0;
        double totalFullPrice = 0;

        for (Map.Entry<String, TicketType> entry : bands.entrySet()) {
            String band = entry.getKey();
            TicketType tickets = entry.getValue();
            double price = bandPrices.getOrDefault(band, 0.0);

            totalChildPrice += price * tickets.getChild();
            totalPensionerPrice += price * tickets.getPensioner();
            totalFullPrice += price * (tickets.getChild() + tickets.getAdult() + tickets.getPensioner());
        }
        log.info("Total prices: Child: {}, Pensioner: {}, Full: {}", totalChildPrice, totalPensionerPrice, totalFullPrice);
        return new PriceBreakdown(totalChildPrice, totalPensionerPrice, totalFullPrice);
    }

    private double calculateSocialClubDiscount(double totalFullPrice, int totalTickets,
                                               Map<String, Double> discountMap) {
        double baseDiscount = discountMap.getOrDefault("SOCIAL_CLUB", 0.0);
        double additionalDiscount = totalTickets > 20
                ? discountMap.getOrDefault("QUANTITY", 0.0)
                : 0.0;
        return totalFullPrice * ((baseDiscount + additionalDiscount) / 100);
    }

    private static class PriceBreakdown {
        double totalChildPrice;
        double totalPensionerPrice;
        double totalFullPrice;

        PriceBreakdown(double totalChildPrice, double totalPensionerPrice, double totalFullPrice) {
            this.totalChildPrice = totalChildPrice;
            this.totalPensionerPrice = totalPensionerPrice;
            this.totalFullPrice = totalFullPrice;
        }
    }

    @Override
    public List<DiscountEntity> getAllDiscounts() {
        log.info("Fetching all discounts");
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getDiscountByType(String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        return discountRepository.findByDiscountType(discountType)
                .orElseThrow(() -> new DiscountNotFoundException("Discount not found for type: " + discountType));
    }

    @Override
    public DiscountEntity createOrUpdateDiscount(DiscountEntity discount) {
        log.info("Creating or updating discount: {}", discount);
        return discountRepository.save(discount);
    }

    @Override
    public void deleteDiscount(String id) {
        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException("Discount not found for ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}