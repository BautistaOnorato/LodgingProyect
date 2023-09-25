package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.repository.CharacteristicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CharacteristicService {
    private final CharacteristicRepository characteristicRepository;

    public Characteristic saveCharacteristic(Characteristic characteristic) {
        return characteristicRepository.save(characteristic);
    }

    public Characteristic updateCharacteristic(Characteristic characteristic) {
        if (characteristicRepository.findById(characteristic.getId()).isPresent()) {
            return characteristicRepository.save(characteristic);
        } else return null;
    }

    public Characteristic findCharacteristicById(Long id) {
        Optional<Characteristic> response = characteristicRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Characteristic> findAllCharacteristics() {
        return characteristicRepository.findAll();
    }

    public void deleteCharacteristic(Long id) {
        characteristicRepository.deleteById(id);
    }
}
