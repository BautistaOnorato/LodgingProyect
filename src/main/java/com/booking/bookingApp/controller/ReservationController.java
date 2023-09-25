package com.booking.bookingApp.controller;

import com.booking.bookingApp.entity.Reservation;
import com.booking.bookingApp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservationss")
public class ReservationController {
    private final ReservationService reservationsService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationsService.findAllReservations());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationsService.findReservationById(id));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(reservationsService.findReservationsByClientId(id));
    }

    @PostMapping
    public ResponseEntity<Reservation> postReservation(@RequestBody Reservation reservations) {
        return ResponseEntity.ok(reservationsService.saveReservation(reservations));
    }

    @PutMapping
    public ResponseEntity<Reservation> putReservation(@RequestBody Reservation reservations) {
        return ResponseEntity.ok(reservationsService.updateReservation(reservations));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationsService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body("Reservation with id: " + id + " has been deleted.");
    }
}
