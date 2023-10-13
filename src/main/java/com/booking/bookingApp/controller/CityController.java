package com.booking.bookingApp.controller;

import com.booking.bookingApp.entity.City;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
@CrossOrigin("*")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.findAllCities());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(cityService.findCityById(id));
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<City> postCity(@RequestBody City city) throws BadRequestException {
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping
    public ResponseEntity<City> putCity(@RequestBody City city) throws ResourceNotFoundException {
        return ResponseEntity.ok(cityService.updateCity(city));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id) throws ResourceNotFoundException {
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body("City with id: " + id + " has been deleted.");
    }
}
