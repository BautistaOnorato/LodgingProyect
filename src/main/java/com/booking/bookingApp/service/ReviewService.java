package com.booking.bookingApp.service;
import com.booking.bookingApp.config.JwtService;
import com.booking.bookingApp.dto.ReviewDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Review;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;

    public Review saveReview(Review review) {
        review.setCreatedAt(LocalDate.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        Optional<Review> response = reviewRepository.findById(review.getId());
        if (response.isPresent()) {
            String email = response.get().getUser().getEmail();
            String username = jwtService.getUsername();
            if (username.equals(email)) {
                return reviewRepository.save(review);
            } else return null;
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

    public void deleteReview(Long id) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            String email = review.get().getUser().getEmail();
            String username = jwtService.getUsername();
            List<String> roles = jwtService.getRolesFromJwt();
            if (roles.contains("ROLE_ADMIN") || email.equals(username)) {
                reviewRepository.deleteById(id);
            } else throw new Exception("La review no fue elieminada.");
        }
    }

    private ReviewDto reviewToReviewDto(Review review) {
        ReviewDto.ReviewUser user = new ReviewDto.ReviewUser(review.getUser().getId(), review.getUser().getFirstName(), review.getUser().getLastName(), review.getUser().getImageUrl());
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
