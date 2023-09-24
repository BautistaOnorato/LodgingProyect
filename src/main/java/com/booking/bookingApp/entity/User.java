package com.booking.bookingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phone;

    @OneToMany(mappedBy = "user")
    private Set<Favourite> favourites;
    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservations;
    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;
}
