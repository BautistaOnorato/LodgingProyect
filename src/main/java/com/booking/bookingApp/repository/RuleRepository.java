package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
