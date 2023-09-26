package com.booking.bookingApp.dto;

import com.booking.bookingApp.entity.Characteristic;
import com.booking.bookingApp.entity.Image;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private String code;
    private ShortProductDto reservationProduct;
    private Long clientId;
}
