package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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
            UserDto user = userToUserDto(response.get());
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String username = authentication.getName();
            if (Objects.equals(user.getEmail(), username)) {
                return user;
            } else return null;
        } else return null;
    }

    public User findUserByEmail(String email) {
        Optional<User> response = userRepository.findByEmail(email);
        if (response.isPresent()) {
            return response.get();
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
                user.getRole(),
                user.getImageUrl(),
                userFavourites
        );
    }
}
