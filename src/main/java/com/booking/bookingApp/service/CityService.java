package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.City;
import com.booking.bookingApp.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(City city) {
        if (cityRepository.findById(city.getId()).isPresent()) {
            return cityRepository.save(city);
        } else return null;
    }

    public City findCityById(Long id) {
        Optional<City> response = cityRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
