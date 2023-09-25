package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long id);
}
