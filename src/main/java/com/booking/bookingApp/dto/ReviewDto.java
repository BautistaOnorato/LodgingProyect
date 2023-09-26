package com.booking.bookingApp.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String description;
    private LocalDate createdAt;
    private Integer stars;
    private Long productId;
    private ReviewUser user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewUser {
        private Long id;
        private String firstName;
        private String lastName;
    }
}
