package com.booking.bookingApp.controller;

import com.booking.bookingApp.entity.City;
import com.booking.bookingApp.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.findAllCities());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findCityById(id));
    }

    @PostMapping
    public ResponseEntity<City> postCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @PutMapping
    public ResponseEntity<City> putCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.updateCity(city));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body("City with id: " + id + " has been deleted.");
    }
}
