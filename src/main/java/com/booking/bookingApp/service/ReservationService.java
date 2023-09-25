package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            return reservationRepository.save(reservation);
        } else return null;
    }

    public Reservation findReservationById(Long id) {
        Optional<Reservation> response = reservationRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findReservationsByClientId(Long id) {
        return reservationRepository.findByClientId(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
