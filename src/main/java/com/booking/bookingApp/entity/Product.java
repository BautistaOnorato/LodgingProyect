package com.booking.bookingApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Double rating;
    @Column
    private String description;
    @Column
    private String cancellationPolicy;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<Security> securities;

    @OneToMany(mappedBy = "product")
    private Set<Rule> rules;

    @OneToMany(mappedBy = "product")
    private Set<Reservation> reservations;

    @OneToMany(mappedBy = "product")
    private Set<SocialNetwork> socialNetworks;

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product")
    private Set<Favourite> favourites;

    @OneToMany(mappedBy = "product")
    private Set<Image> images;

    @ManyToMany
    @JoinTable(name = "product_x_characteristic",
        joinColumns = { @JoinColumn(name = "product_id") },
        inverseJoinColumns = { @JoinColumn(name = "characteristic_id") }
    )
    private Set<Characteristic> characteristics;

}
