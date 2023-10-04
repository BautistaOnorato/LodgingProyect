package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.CharacteristicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CharacteristicService {
    private final CharacteristicRepository characteristicRepository;

    public Characteristic saveCharacteristic(Characteristic characteristic) throws BadRequestException {
        try {
            return characteristicRepository.save(characteristic);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The characteristic was not created");
        }
    }

    public Characteristic updateCharacteristic(Characteristic characteristic) throws ResourceNotFoundException {
        if (characteristicRepository.findById(characteristic.getId()).isPresent()) {
            return characteristicRepository.save(characteristic);
        } else throw new ResourceNotFoundException("Something went wrong. The characteristic with id: " + characteristic.getId() + " does not exist.");
    }

    public Characteristic findCharacteristicById(Long id) throws ResourceNotFoundException {
        Optional<Characteristic> response = characteristicRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else throw new ResourceNotFoundException("Something went wrong. The characteristic with id: " + id + " does not exist.");
    }

    public List<Characteristic> findAllCharacteristics() {
        return characteristicRepository.findAll();
    }

    public void deleteCharacteristic(Long id) throws ResourceNotFoundException {
        Optional<Characteristic> response = characteristicRepository.findById(id);
        if (response.isPresent()) {
            characteristicRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The characteristic with id: " + id + " does not exist.");
    }
}
