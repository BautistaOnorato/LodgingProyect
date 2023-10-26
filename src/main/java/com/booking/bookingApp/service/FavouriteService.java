package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.FavouriteRepository;
import com.booking.bookingApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;

    public UserDto.UserFavourite saveFavourite(Favourite favourite) throws BadRequestException {
        try {
            favouriteRepository.save(favourite);
            return new UserDto.UserFavourite(favourite.getId(), favourite.getProduct().getId());
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The favourite was not created.");
        }
    }

    public FavouriteDto findFavouriteById(Long id) throws ResourceNotFoundException {
        Optional<Favourite> response = favouriteRepository.findById(id);
        if (response.isPresent()) {
            return favouriteToFavouriteDto(response.get());
        } else throw new ResourceNotFoundException("Something went wrong. The favourite with id: " + id + " does not exist.");
    }

    public List<Favourite> findAllFavourites() {
        return favouriteRepository.findAll();
    }
    public List<FavouriteDto> findByUserId(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Favourite> favourites = favouriteRepository.findByUserId(id);
            List<FavouriteDto> favouriteDtos = new ArrayList<>();
            for (Favourite favourite : favourites) {
                favouriteDtos.add(favouriteToFavouriteDto(favourite));
            }
            return favouriteDtos;
        } else throw new ResourceNotFoundException("Something went wrong. The user with id: " + id + " does not exist.");
    }

    public void deleteFavourite(Long id) throws ResourceNotFoundException {
        Optional<Favourite> favourite = favouriteRepository.findById(id);
        if (favourite.isPresent()) {
            favouriteRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The favourite with id: " + id + " does not exist.");
    }


    private FavouriteDto favouriteToFavouriteDto(Favourite favourite) {
        ShortProductDto favouriteProduct = new ShortProductDto(
                favourite.getProduct().getId(),
                favourite.getProduct().getTitle(),
                favourite.getProduct().getLocation(),
                favourite.getProduct().getImages(),
                favourite.getProduct().getRating()
        );

        return new FavouriteDto(
                favourite.getUser().getId(),
                favouriteProduct
        );
    }
}
