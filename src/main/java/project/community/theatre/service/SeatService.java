package project.community.theatre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.community.theatre.constant.AppConstants;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${app.seat.lock.expiry}")
    private String lockExpiry;

    public Map<String, String> lockSeats(String showId, List<String> seatNumbers) {
        Map<String, String> results = new HashMap<>();

        for (String seatNumber : seatNumbers) {
            String key = AppConstants.REDIS_SEAT_LOCK_PREFIX + showId + ":" + seatNumber;

            // Check if the seat is already locked
            Boolean isLocked = redisTemplate.hasKey(key);
            if (Boolean.TRUE.equals(isLocked)) {
                results.put(seatNumber, "Already Locked");
            } else {
                // Lock the seat for 5 minutes
                redisTemplate.opsForValue().set(key, "LOCKED", Long.parseLong(lockExpiry), TimeUnit.MINUTES);
                results.put(seatNumber, "Locked Successfully");
            }
        }

        return results;
    }

    public List<String> checkSeatsAvailability(String showId, List<String> seatNumbers) {
        return seatNumbers.stream()
                .filter(seatNumber -> !Boolean.TRUE.equals(redisTemplate.hasKey(AppConstants.REDIS_SEAT_LOCK_PREFIX + showId + ":" + seatNumber)))
                .collect(Collectors.toList());
    }
}