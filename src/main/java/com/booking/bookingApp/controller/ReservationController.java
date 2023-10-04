package com.booking.bookingApp.controller;

import com.booking.bookingApp.dto.ReservationDto;
import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationsService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationsService.findAllReservations());
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/id/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationsService.findReservationById(id));
    }

    @PreAuthorize("hasAuthority('admin:read') or hasRole('USER')")
    @GetMapping("/client/{id}")
    public ResponseEntity<List<ReservationDto>> getReservationsByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(reservationsService.findReservationsByClientId(id));
    }

    @PreAuthorize("hasAuthority('admin:create') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<Reservation> postReservation(@RequestBody Reservation reservations) {
        return ResponseEntity.ok(reservationsService.saveReservation(reservations));
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping
    public ResponseEntity<Reservation> putReservation(@RequestBody Reservation reservations) {
        return ResponseEntity.ok(reservationsService.updateReservation(reservations));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationsService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body("Reservation with id: " + id + " has been deleted.");
    }
}
