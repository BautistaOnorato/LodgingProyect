package com.booking.bookingApp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Product.class)
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Security> securities = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Rule> rules = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<SocialNetwork> socialNetworks = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Favourite> favourites = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "product_x_characteristic",
        joinColumns = { @JoinColumn(name = "product_id") },
        inverseJoinColumns = { @JoinColumn(name = "characteristic_id") }
    )
    private Set<Characteristic> characteristics = new HashSet<>();

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setProduct(null);
    }

    public void removeSecurity(Security security) {
        this.securities.remove(security);
        security.setProduct(null);
    }

    public void removeRule(Rule rule) {
        this.rules.remove(rule);
        rule.setProduct(null);
    }

    public void removeSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetworks.remove(socialNetwork);
        socialNetwork.setProduct(null);
    }

}
