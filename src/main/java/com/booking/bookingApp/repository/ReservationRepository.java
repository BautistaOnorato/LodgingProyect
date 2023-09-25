package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long id);
}
