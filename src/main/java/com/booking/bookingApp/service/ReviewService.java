package com.booking.bookingApp.service;
import com.booking.bookingApp.entity.Review;
import com.booking.bookingApp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        if (reviewRepository.findById(review.getId()).isPresent()) {
            return reviewRepository.save(review);
        } else return null;
    }

    public Review findReviewById(Long id) {
        Optional<Review> response = reviewRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> findReviewsByProductId(Long id) {
        return reviewRepository.findByProductId(id);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
