package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.ReservationDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        reservation.setCode(UUID.randomUUID().toString());
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            return reservationRepository.save(reservation);
        } else return null;
    }

    public ReservationDto findReservationById(Long id) {
        Optional<Reservation> response = reservationRepository.findById(id);
        if (response.isPresent()) {
            return reservationToReservationDto(response.get());
        } else return null;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<ReservationDto> findReservationsByClientId(Long id) {
        List<Reservation> reservations = reservationRepository.findByClientId(id);
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(reservationToReservationDto(reservation));
        }
        return reservationDtos;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private ReservationDto reservationToReservationDto(Reservation reservation) {
        ShortProductDto reservationProduct = new ShortProductDto(
                reservation.getProduct().getId(),
                reservation.getProduct().getTitle(),
                reservation.getProduct().getCategory().getTitle(),
                reservation.getProduct().getImages(),
                reservation.getProduct().getCharacteristics()
        );

        return new ReservationDto(
                reservation.getId(),
                reservation.getInitialDate(),
                reservation.getFinalDate(),
                reservation.getCode(),
                reservationProduct,
                reservation.getClient().getId()
        );
    }
}
