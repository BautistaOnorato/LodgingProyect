package com.booking.bookingApp.controller;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characteristics")
public class CharacteristicController {
    private final CharacteristicService characteristicService;

    @GetMapping
    public ResponseEntity<List<Characteristic>> getCharacteristics() {
        return ResponseEntity.ok(characteristicService.findAllCharacteristics());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Characteristic> getCharacteristicById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(characteristicService.findCharacteristicById(id));
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<Characteristic> postCharacteristic(@RequestBody Characteristic characteristic) throws BadRequestException {
        return ResponseEntity.ok(characteristicService.saveCharacteristic(characteristic));
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping
    public ResponseEntity<Characteristic> putCharacteristic(@RequestBody Characteristic characteristic) throws ResourceNotFoundException {
        return ResponseEntity.ok(characteristicService.updateCharacteristic(characteristic));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCharacteristic(@PathVariable Long id) throws ResourceNotFoundException {
        characteristicService.deleteCharacteristic(id);
        return ResponseEntity.status(HttpStatus.OK).body("Characteristic with id: " + id + " has been deleted.");
    }
}
