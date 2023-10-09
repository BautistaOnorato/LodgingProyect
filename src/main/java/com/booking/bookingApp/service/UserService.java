package com.booking.bookingApp.service;

import com.booking.bookingApp.config.JwtService;
import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.entity.Role;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    public User saveUser(User user) throws BadRequestException {
        try {
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The user was not created.");
        }
    }

    public User updateUser(User user) throws ResourceNotFoundException, AccessDeniedException {
        Optional<User> response = userRepository.findById(user.getId());
        if (response.isPresent()) {
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(response.get().getEmail())) {
                user.setPassword(response.get().getPassword());
                return userRepository.save(user);
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The user with id: " + user.getId() + " does not exist.");
    }

    public UserDto findUserById(Long id) throws ResourceNotFoundException, AccessDeniedException {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            UserDto user = userToUserDto(response.get());
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                return user;
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The user with id: " + id + " does not exist.");
    }

    public User findUserByEmail(String email) throws ResourceNotFoundException, AccessDeniedException {
        Optional<User> response = userRepository.findByEmail(email);
        if (response.isPresent()) {
            User user = response.get();
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                return user;
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The user with email: " + email + " does not exist.");
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) throws ResourceNotFoundException, AccessDeniedException {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            User user = response.get();
            List<String> roles = jwtService.getRolesFromJwt();
            String username = jwtService.getUsername();
            System.out.println(roles.contains("ROLE_ADMIN"));
            System.out.println(username);
            if (roles.contains("ROLE_ADMIN") || username.equals(user.getEmail())) {
                userRepository.deleteById(id);
            } else throw new AccessDeniedException("Cannot access this information.");
        } else throw new ResourceNotFoundException("Something went wrong. The user with id: " + id + " does not exist.");
    }

    public UserDto userToUserDto(User user) {
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
                user.isEnabled(),
                userFavourites
        );
    }
}
