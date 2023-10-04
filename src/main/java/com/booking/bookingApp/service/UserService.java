package com.booking.bookingApp.service;

import com.booking.bookingApp.config.JwtService;
import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Role;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> response = userRepository.findById(user.getId());
        if (response.isPresent()) {
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(response.get().getEmail())) {
                user.setPassword(response.get().getPassword());
                return userRepository.save(user);
            } else return null;
        } else return null;
    }

    public UserDto findUserById(Long id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            UserDto user = userToUserDto(response.get());
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                return user;
            } else return null;
        } else return null;
    }

    public User findUserByEmail(String email) {
        Optional<User> response = userRepository.findByEmail(email);
        if (response.isPresent()) {
            User user = response.get();
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                return user;
            } else return null;
        } else return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) throws Exception {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            User user = response.get();
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            System.out.println(roles.contains("ROLE_ADMIN"));
            System.out.println(username);
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                userRepository.deleteById(id);
            } else {
                throw new Exception("No se pudo eliminar el usuario");
            }
        }
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
