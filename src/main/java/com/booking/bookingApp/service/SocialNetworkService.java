package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.SocialNetwork;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.SocialNetworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SocialNetworkService {
    private final SocialNetworkRepository imageRepository;

    public void removeSocialNetwork(Long id) {
        Optional<SocialNetwork> optionalSocialNetwork = imageRepository.findById(id);
        if (optionalSocialNetwork.isPresent()) {
            optionalSocialNetwork.get().getProduct().removeSocialNetwork(optionalSocialNetwork.get());
            imageRepository.deleteById(id);
        }
    }

    public void saveSocialNetworks(Set<SocialNetwork> images, Product product) {
        if (images != null) {
            for (SocialNetwork image : images) {
                image.setProduct(product);
                imageRepository.save(image);
            }
        }
    }
}
