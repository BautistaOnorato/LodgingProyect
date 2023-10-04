package com.booking.bookingApp.controller;

import com.booking.bookingApp.dto.UserDto;
import com.booking.bookingApp.entity.User;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('admin:read')")
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('admin:read')")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserById(@PathVariable String email) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user) throws BadRequestException {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('admin:update')")
    @PatchMapping
    public ResponseEntity<User> patchUser(@RequestBody User user) throws ResourceNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PreAuthorize("hasRole('USER') or hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws ResourceNotFoundException, AccessDeniedException {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User with id: " + id + " has been deleted.");
    }
}
