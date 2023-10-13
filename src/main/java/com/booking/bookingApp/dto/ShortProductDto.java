package com.booking.bookingApp.dto;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.entity.Image;
import com.booking.bookingApp.entity.Location;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortProductDto {
    private Long id;
    private String title;
    private Location location;
    private Set<Image> images = new HashSet<>();
    private Double rating;
}
