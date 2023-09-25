package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Category;
import com.booking.bookingApp.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
}
