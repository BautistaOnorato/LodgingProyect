package com.booking.bookingApp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteDto {
    private Long userId;
    private ShortProductDto product;
}
