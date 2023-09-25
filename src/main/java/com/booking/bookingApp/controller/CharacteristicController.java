package com.booking.bookingApp.controller;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Characteristic> getCharacteristicById(@PathVariable Long id) {
        return ResponseEntity.ok(characteristicService.findCharacteristicById(id));
    }

    @PostMapping
    public ResponseEntity<Characteristic> postCharacteristic(@RequestBody Characteristic characteristic) {
        return ResponseEntity.ok(characteristicService.saveCharacteristic(characteristic));
    }

    @PutMapping
    public ResponseEntity<Characteristic> putCharacteristic(@RequestBody Characteristic characteristic) {
        return ResponseEntity.ok(characteristicService.updateCharacteristic(characteristic));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCharacteristic(@PathVariable Long id) {
        characteristicService.deleteCharacteristic(id);
        return ResponseEntity.status(HttpStatus.OK).body("Characteristic with id: " + id + " has been deleted.");
    }
}
