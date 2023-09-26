package com.booking.bookingApp.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Set<UserFavourite> favouriteProducts = new HashSet<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserFavourite {
        private Long productId;
    }
}

