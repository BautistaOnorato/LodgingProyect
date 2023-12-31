package com.booking.bookingApp.controller;

import com.booking.bookingApp.dto.ReviewDto;
import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.entity.Review;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
@CrossOrigin("*")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        return ResponseEntity.ok(reviewService.findAllReviews());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reviewService.findReviewById(id));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reviewService.findReviewsByProductId(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Review> postReview(@RequestBody Review review) throws BadRequestException {
        return ResponseEntity.ok(reviewService.saveReview(review));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<Review> putReview(@RequestBody Review review) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(reviewService.updateReview(review));
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) throws AccessDeniedException, ResourceNotFoundException {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body("Review with id: " + id + " has been deleted.");
    }
}

