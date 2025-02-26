package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.community.theatre.service.EventService;
import project.community.theatre.service.SeatService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    EventService eventService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${app.seat.lock.expiry}")
    private String tempLockExpiry;
    @Value("${app.redis.temp.seat.lock.key}")
    private String tempSeatLockKeyFormat;
    @Value("${app.redis.booked.seat.lock.key}")
    private String bookedSeatLockKeyFormat;

    @Override
    public void lockSeats(String eventId, String showId, List<String> seatNumbers) {
        log.info("Locking seats for show ID: {} and seats: {}", showId, seatNumbers);
        // Lock all requested seats in Redis
        for (String seat : seatNumbers) {
            String key = tempSeatLockKeyFormat
                    .replace("{eventId}", eventId)
                    .replace("{showId}", showId)
                    .replace("{seat}", seat);
            redisTemplate.opsForValue().set(key, "LOCKED", Long.parseLong(tempLockExpiry), TimeUnit.SECONDS);
        }
    }

    @Override
    public List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking availability of seats for show ID: {} and seats: {}", showId, seatNumbers);
        return seatNumbers.stream()
                .filter(seatNumber -> {
                    String key = tempSeatLockKeyFormat
                            .replace("{eventId}", eventId)
                            .replace("{showId}", showId)
                            .replace("{seat}", seatNumber);

                    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking and locking seats for show ID: {} and seats: {}", showId, seatNumbers);

        List<String> unavailableSeats = checkSeatsAvailability(eventId, showId, seatNumbers);

        // If any seats are unavailable, return them
        if (!unavailableSeats.isEmpty()) {
            log.warn("Unavailable seats found: {}", unavailableSeats);
            return Map.of(
                    "status", Boolean.FALSE,
                    "unavailableSeats", unavailableSeats
            );
        }
        // Lock all requested seats in Redis
        lockSeats(eventId, showId, seatNumbers);

        log.info("All seats locked successfully: {}", seatNumbers);
        return Map.of(
                "status", Boolean.TRUE
        );
    }

    @Override
    public void lockBookedSeats(String eventId, String showId, List<String> bookedSeats) {
        log.info("Locking booked seats for show ID {}: {}", showId, bookedSeats);
        String redisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        Boolean keyExists = redisTemplate.hasKey(redisKey);
        if (Boolean.TRUE.equals(keyExists)) {
            log.info("Appending {} seats to the existing list for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
        } else {
            log.info("Creating a new list with {} seats for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
            // Set the TTL for the key (only when creating a new list)
            redisTemplate.expire(redisKey, getEventExpirationTime(eventId), TimeUnit.SECONDS);
        }
        log.info("Successfully locked {} seats for show ID {}", bookedSeats.size(), showId);
    }

    @Override
    public List<String> getAllBookedSeats(String eventId, String showId) {
        log.info("Fetching booked and locked seats for event ID: {} and show ID: {}", eventId, showId);

        // Get booked seats from the list stored under the booked key
        String bookedRedisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        List<String> bookedSeats = redisTemplate.opsForList().range(bookedRedisKey, 0, -1);
        if (bookedSeats == null) {
            bookedSeats = Collections.emptyList();
            log.info("No booked seats found for event ID: {} and show ID: {}", eventId, showId);
        } else {
            log.info("Found {} booked seats for event ID: {} and show ID: {}", bookedSeats.size(), eventId, showId);
        }

        Set<String> lockKeys = getSeatLockKeys(eventId, showId);
        Set<String> lockedSeats = new HashSet<>();
        for (String key : lockKeys) {
            String seat = key.substring(key.lastIndexOf(':') + 1);
            lockedSeats.add(seat);
        }
        log.info("Found {} locked seats.", lockedSeats.size());

        Set<String> allLockedSeats = new HashSet<>(bookedSeats);
        allLockedSeats.addAll(lockedSeats);
        log.info("Total booked and locked seats: {}", allLockedSeats.size());

        return new ArrayList<>(allLockedSeats);
    }

    private long getEventExpirationTime(String eventId) {
        LocalDate endDate = eventService.getEvent(eventId).getEndDate();
        // Assume the end time is at the end of the day (23:59:59)
        long endTimeMillis = endDate.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        // Calculate the remaining time in seconds
        long currentTimeMillis = System.currentTimeMillis();
        return ChronoUnit.SECONDS.between(
                Instant.ofEpochMilli(currentTimeMillis),
                Instant.ofEpochMilli(endTimeMillis)
        );
    }

    private Set<String> getSeatLockKeys(String eventId, String showId) {
        // Construct the pattern with the known eventId and showId
        String pattern = tempSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId)
                .replace("{seat}", "*");

        return redisTemplate.keys(pattern);
    }
}