package project.community.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import project.community.theatre.service.BandService;
import project.community.theatre.service.DiscountService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private BandService bandService;
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public DiscountResponse calculateDiscount(DiscountRequest request) {
        log.info("Calculating discount for request: {}", request);
        DiscountResponse response = new DiscountResponse();
        List<BandEntity> bands = bandService.getAllBands();
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

        calculateRegularDiscounts(request, prices, discountMap, response);

        double totalReduction = response.getChild() + response.getPensioner() +
                response.getLastHour() + response.getWeekday();
        response.setReduction(totalReduction);
        response.setFinalPrice(prices.totalFullPrice - totalReduction);

        log.info("Regular discounts applied: {}", response);
        return response;
    }

    // Rest of the code remains unchanged
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

    private void calculateRegularDiscounts(DiscountRequest request, PriceBreakdown prices,
                                           Map<String, Double> discountMap, DiscountResponse response) {
        // Child and Pensioner discounts
        double childDiscountPercent = discountMap.getOrDefault("CHILDREN", 0.0);
        double pensionerDiscountPercent = discountMap.getOrDefault("PENSIONERS", 0.0);
        response.setChild(prices.totalChildPrice * (childDiscountPercent / 100));
        response.setPensioner(prices.totalPensionerPrice * (pensionerDiscountPercent / 100));

        // Last Hour discount
        if (isLastHour(request.getShowTime())) {
            double lastHourPercent = discountMap.getOrDefault("LAST_HOUR", 0.0);
            response.setLastHour(prices.totalFullPrice * (lastHourPercent / 100));
        }

        // Weekday discount
        if (isWeekday(request.getDay())) {
            double weekdayPercent = discountMap.getOrDefault("WEEKDAY_SPECIAL", 0.0);
            response.setWeekday(prices.totalFullPrice * (weekdayPercent / 100));
        }
    }

    private boolean isLastHour(LocalDateTime showTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursUntilShow = ChronoUnit.HOURS.between(currentTime, showTime);
        return hoursUntilShow <= 1;
    }

    private boolean isWeekday(String day) {
        List<String> weekdays = Arrays.asList("monday", "tuesday", "wednesday", "thursday");
        return weekdays.contains(day.toLowerCase());
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
    @Transactional
    public void deleteDiscount(String id) {
        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException("Discount not found for ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}