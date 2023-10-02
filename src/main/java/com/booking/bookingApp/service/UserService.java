package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        user.setActive(true);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else return null;
    }

    public UserDto findUserById(Long id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return userToUserDto(response.get());
        } else return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.updateActive(false, id);
    }

    private UserDto userToUserDto(User user) {
        Set<UserDto.UserFavourite> userFavourites = new HashSet<>();
        for (Favourite favourite : user.getFavourites()) {
            userFavourites.add(new UserDto.UserFavourite(favourite.getProduct().getId()));
        }
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                userFavourites
        );
    }
}
