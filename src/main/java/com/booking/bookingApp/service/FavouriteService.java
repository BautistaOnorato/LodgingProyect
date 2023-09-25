package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;

    public Favourite saveFavourite(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    public Favourite findFavouriteById(Long id) {
        Optional<Favourite> response = favouriteRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Favourite> findAllFavourites() {
        return favouriteRepository.findAll();
    }

    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }
}
