package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.City;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;

    public City saveCity(City city) throws BadRequestException {
        try {
            return cityRepository.save(city);
        } catch (Exception exception) {
            throw new BadRequestException("Something went wrong. The city was not created");
        }
    }

    public City updateCity(City city) throws ResourceNotFoundException {
        if (cityRepository.findById(city.getId()).isPresent()) {
            return cityRepository.save(city);
        } else throw new ResourceNotFoundException("Something went wrong. The city with id: " + city.getId() + " does not exist.");
    }

    public City findCityById(Long id) throws ResourceNotFoundException {
        Optional<City> response = cityRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else throw new ResourceNotFoundException("Something went wrong. The city with id: " + id + " does not exist.");
    }

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public void deleteCity(Long id) throws ResourceNotFoundException {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            cityRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The city with id: " + id + " does not exist.");
    }
}
