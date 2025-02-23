package project.community.theatre.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.community.theatre.constant.AppConstants;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${app.seat.lock.expiry}")
    private String lockExpiry;

    @Override
    public void lockSeats(String showId, List<String> seatNumbers) {
        log.info("Locking seats for show ID: {} and seats: {}", showId, seatNumbers);

        // Key for Redis (e.g., "show:<showId>:seats")
        String redisKey = "show:" + showId + ":seats";

        // Lock all requested seats in Redis
        seatNumbers.forEach(seat -> redisTemplate.opsForHash().put(redisKey, seat, "LOCKED"));
    }

    @Override
    public List<String> checkSeatsAvailability(String showId, List<String> seatNumbers) {
        return seatNumbers.stream()
                .filter(seatNumber -> Boolean.TRUE.equals(redisTemplate.hasKey(AppConstants.REDIS_SEAT_LOCK_PREFIX + showId + ":" + seatNumber)))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> processSeatsAvailability(String showId, List<String> seatNumbers) {
        log.info("Checking and locking seats for show ID: {} and seats: {}", showId, seatNumbers);

        List<String> unavailableSeats = checkSeatsAvailability(showId, seatNumbers);

        // If any seats are unavailable, return them
        if (!unavailableSeats.isEmpty()) {
            log.warn("Unavailable seats found: {}", unavailableSeats);
            return Map.of(
                    "status", Boolean.FALSE,
                    "unavailableSeats", unavailableSeats
            );
        }

        // Lock all requested seats in Redis
        lockSeats(showId, seatNumbers);
        log.info("All seats locked successfully: {}", seatNumbers);
        return Map.of(
                "status", Boolean.TRUE
        );
    }

}