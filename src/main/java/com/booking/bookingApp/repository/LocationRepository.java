package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
