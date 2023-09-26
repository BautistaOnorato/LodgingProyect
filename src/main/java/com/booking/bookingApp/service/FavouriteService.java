package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;

    public Favourite saveFavourite(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    public FavouriteDto findFavouriteById(Long id) {
        Optional<Favourite> response = favouriteRepository.findById(id);
        if (response.isPresent()) {
            return favouriteToFavouriteDto(response.get());
        } else return null;
    }

    public List<Favourite> findAllFavourites() {
        return favouriteRepository.findAll();
    }
    public List<FavouriteDto> findByUserId(Long id) {
        List<Favourite> favourites = favouriteRepository.findByUserId(id);
        List<FavouriteDto> favouriteDtos = new ArrayList<>();
        for (Favourite favourite : favourites) {
            favouriteDtos.add(favouriteToFavouriteDto(favourite));
        }
        return favouriteDtos;
    }

    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }

    private FavouriteDto favouriteToFavouriteDto(Favourite favourite) {
        ShortProductDto favouriteProduct = new ShortProductDto(
                favourite.getProduct().getId(),
                favourite.getProduct().getTitle(),
                favourite.getProduct().getCategory().getTitle(),
                favourite.getProduct().getImages(),
                favourite.getProduct().getCharacteristics()
        );

        return new FavouriteDto(
                favourite.getUser().getId(),
                favouriteProduct
        );
    }
}
