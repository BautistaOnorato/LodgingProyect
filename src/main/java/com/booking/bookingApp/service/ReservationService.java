package com.booking.bookingApp.service;

import com.booking.bookingApp.config.JwtService;
import com.booking.bookingApp.dto.ReservationDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.ReservationRepository;
import com.booking.bookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public Reservation saveReservation(Reservation reservation) throws BadRequestException {
        try {
            reservation.setCode(UUID.randomUUID().toString());
            return reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The reservation was not created.");
        }
    }

    public Reservation updateReservation(Reservation reservation) throws ResourceNotFoundException {
        if (reservationRepository.findById(reservation.getId()).isPresent()) {
            return reservationRepository.save(reservation);
        } else throw new ResourceNotFoundException("Something went wrong. The reservation with id: " + reservation.getId() + " does not exist.");
    }

    public ReservationDto findReservationById(Long id) throws ResourceNotFoundException {
        Optional<Reservation> response = reservationRepository.findById(id);
        if (response.isPresent()) {
            return reservationToReservationDto(response.get());
        } else throw new ResourceNotFoundException("Something went wrong. The reservation with id: " + id + " does not exist.");
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<ReservationDto> findReservationsByClientId(Long id) throws AccessDeniedException, ResourceNotFoundException {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            String username = jwtService.getUsername();
            List<String> roles = jwtService.getRolesFromJwt();
            if (roles.contains("ROLE_ADMIN") || user.get().getEmail().equals(username)) {
                List<Reservation> reservations = reservationRepository.findByClientId(id);
                for (Reservation reservation : reservations) {
                    reservationDtos.add(reservationToReservationDto(reservation));
                }
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The user with id: " + id + " does not exist.");
        return reservationDtos;
    }

    public void deleteReservation(Long id) throws ResourceNotFoundException {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The reservation with id: " + id + " does not exist.");
    }

    private ReservationDto reservationToReservationDto(Reservation reservation) {
        ShortProductDto reservationProduct = new ShortProductDto(
                reservation.getProduct().getId(),
                reservation.getProduct().getTitle(),
                reservation.getProduct().getLocation(),
                reservation.getProduct().getImages(),
                reservation.getProduct().getRating()
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
