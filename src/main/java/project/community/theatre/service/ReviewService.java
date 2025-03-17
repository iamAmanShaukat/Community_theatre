package project.community.theatre.service;

import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewService {

    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId);
    public List<ReviewEntity> getAllReviews(String eventId);
}
