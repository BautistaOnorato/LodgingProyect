package com.booking.bookingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column
    private String street;
    @Column
    private String streetNumber;
    @Column
    private String floor;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    @OneToOne(mappedBy = "location")
    private Product product;
}
