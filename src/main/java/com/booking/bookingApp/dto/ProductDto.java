package com.booking.bookingApp.dto;

import com.booking.bookingApp.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String title;
    private Double rating;
    private String description;
    private String cancellationPolicy;
    private Location location;
    private Category category;
    private Set<Security> securities = new HashSet<>();
    private Set<Rule> rules = new HashSet<>();
    private Set<ProductReservationDto> reservations = new HashSet<>();
    private Set<SocialNetwork> socialNetworks = new HashSet<>();
    private Set<Image> images = new HashSet<>();
    private Set<Characteristic> characteristics = new HashSet<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductReservationDto {
        private Long id;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate initialDate;
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate finalDate;
    }
}


