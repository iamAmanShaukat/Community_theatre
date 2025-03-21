package project.community.theatre.service;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewService {

    /**
     * This method saves a review for an event. It will first check if the event
     * exists. If the event exists, it will then create a new review and save it
     * to the database.
     *
     * @param userName   the username of the user who is submitting the review
     * @param rating     the rating of the review
     * @param description the description of the review
     * @param eventId    the id of the event to which the review is being submitted
     * @return a ReviewEntity that has been saved to the database
     */
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId);
    public List<ReviewResponseDto> getAllReviews(String eventId);
}
