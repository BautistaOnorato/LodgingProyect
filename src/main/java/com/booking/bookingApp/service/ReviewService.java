package com.booking.bookingApp.service;
import com.booking.bookingApp.config.JwtService;
import com.booking.bookingApp.dto.ReviewDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.entity.Review;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.ProductRepository;
import com.booking.bookingApp.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
    private final ProductRepository productRepository;
    private final JwtService jwtService;

    public Review saveReview(Review review) throws BadRequestException {
        try {
            review.setCreatedAt(LocalDate.now());
            return reviewRepository.save(review);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The review was not created.");
        }
    }

    public Review updateReview(Review review) throws ResourceNotFoundException, AccessDeniedException {
        Optional<Review> response = reviewRepository.findById(review.getId());
        if (response.isPresent()) {
            String email = response.get().getUser().getEmail();
            String username = jwtService.getUsername();
            if (username.equals(email)) {
                return reviewRepository.save(review);
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The review with id: " + review.getId() + " does not exist.");
    }

    public ReviewDto findReviewById(Long id) throws ResourceNotFoundException {
        Optional<Review> response = reviewRepository.findById(id);
        if (response.isPresent()) {
            return reviewToReviewDto(response.get());
        } else throw new ResourceNotFoundException("Something went wrong. The review with id: " + id + " does not exist.");
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public List<ReviewDto> findReviewsByProductId(Long id) throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            List<Review> reviews = reviewRepository.findByProductId(id);
            List<ReviewDto> reviewDtos = new ArrayList<>();
            for (Review review : reviews) {
                reviewDtos.add(reviewToReviewDto(review));
            }
            return reviewDtos;
        } else throw new ResourceNotFoundException("Something went wrong. The product with id: " + id + " does not exist.");
    }

    public void deleteReview(Long id) throws ResourceNotFoundException, AccessDeniedException {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            String email = review.get().getUser().getEmail();
            String username = jwtService.getUsername();
            List<String> roles = jwtService.getRolesFromJwt();
            if (roles.contains("ROLE_ADMIN") || email.equals(username)) {
                reviewRepository.deleteById(id);
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The review with id: " + id + " does not exist.");
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
