package com.booking.bookingApp.repository;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findByUserId(Long id);
}
