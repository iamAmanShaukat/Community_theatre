package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.ReviewRequestDto;
import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * This method saves a new review for an event.
     *
     * @param request A ReviewRequestDto object containing the details of the review to be saved.
     * @return A ResponseEntity containing the newly saved ReviewEntity object.
     */
    @PostMapping("/save")
    public ResponseEntity<ReviewEntity> saveReview(@RequestBody ReviewRequestDto request) {
        log.info("Received request to save review for event ID: {}", request.getEventId());

        // Save the review
        ReviewEntity savedReview = reviewService.saveReview(
                request.getUserName(),
                request.getRating(),
                request.getDescription(),
                request.getEventId()
        );

        return ResponseEntity.ok(savedReview);
    }

    /**
     * This method fetches all reviews for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch reviews for.
     * @return A ResponseEntity containing a list of ReviewEntity objects.
     */
    @GetMapping("/all/{eventId}")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(@PathVariable String eventId) {
        log.info("Received request to fetch all reviews for event ID: {}", eventId);

        // Fetch all reviews for the event
        List<ReviewResponseDto> reviews = reviewService.getAllReviews(eventId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}