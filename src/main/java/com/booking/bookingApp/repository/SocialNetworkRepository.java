package com.booking.bookingApp.repository;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.SocialNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Long> {
}
