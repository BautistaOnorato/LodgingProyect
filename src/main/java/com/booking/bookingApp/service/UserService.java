package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else return null;
    }

    public User findUserById(Long id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
