package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ReviewRepository;
import project.community.theatre.service.ReviewService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;

    @Transactional
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId) {
        log.info("Saving review for event ID: {}", eventId);

        // Fetch the event to ensure it exists
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Set default username if empty
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Anonymous";
        }

        // Create and save the review
        ReviewEntity review = ReviewEntity.builder()
                .reviewId(UUID.randomUUID().toString())
                .userName(userName)
                .rating(rating)
                .description(description)
                .reviewedDate(LocalDate.now())
                .event(event)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseDto> getAllReviews(String eventId) {
        log.info("Fetching reviews for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getReviewId().stream()
                .map(ReviewEntity -> ReviewResponseDto.builder()
                        .reviewId(ReviewEntity.getReviewId())
                        .userName(ReviewEntity.getUserName())
                        .rating(ReviewEntity.getRating())
                        .description(ReviewEntity.getDescription())
                        .reviewDate(ReviewEntity.getReviewedDate())
                        .build())
                .toList();
    }
}