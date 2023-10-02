package com.booking.bookingApp.service;
import com.booking.bookingApp.dto.ReviewDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Review;
import com.booking.bookingApp.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        review.setCreatedAt(LocalDate.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        if (reviewRepository.findById(review.getId()).isPresent()) {
            return reviewRepository.save(review);
        } else return null;
    }

    public ReviewDto findReviewById(Long id) {
        Optional<Review> response = reviewRepository.findById(id);
        if (response.isPresent()) {
            return reviewToReviewDto(response.get());
        } else return null;
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public List<ReviewDto> findReviewsByProductId(Long id) {
        List<Review> reviews = reviewRepository.findByProductId(id);
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(reviewToReviewDto(review));
        }
        return reviewDtos;
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

//    @Transactional
//    public void deleteList(Iterable<Review> reviews) {
//        reviewRepository.deleteAll(reviews);
//    }

    private ReviewDto reviewToReviewDto(Review review) {
        ReviewDto.ReviewUser user = new ReviewDto.ReviewUser(review.getUser().getId(), review.getUser().getFirstName(), review.getUser().getLastName());
        return new ReviewDto(
                review.getId(),
                review.getDescription(),
                review.getCreatedAt(),
                review.getStars(),
                review.getProduct().getId(),
                user
        );
    }
}
