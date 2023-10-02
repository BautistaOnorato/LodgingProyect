package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.SocialNetwork;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.SocialNetworkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SocialNetworkService {
    private final SocialNetworkRepository socialNetworkRepository;

    @Transactional
    public void removeSocialNetworks(Set<SocialNetwork> oldSocialNetworks, Set<SocialNetwork> newSocialNetworks) {
        List<SocialNetwork> deleteSocialNetworks = new ArrayList<>();
        for (SocialNetwork oldSocialNetwork : oldSocialNetworks) {
            boolean find = false;
            for (SocialNetwork newSocialNetwork : newSocialNetworks) {
                if (Objects.equals(oldSocialNetwork.getId(), newSocialNetwork.getId())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleteSocialNetworks.add(oldSocialNetwork);
            }
        }

        socialNetworkRepository.deleteAll(deleteSocialNetworks);
    }
    public void saveSocialNetworks(Set<SocialNetwork> socialNetworks, Product product) {
        if (socialNetworks != null) {
            for (SocialNetwork socialNetwork : socialNetworks) {
                socialNetwork.setProduct(product);
                socialNetworkRepository.save(socialNetwork);
            }
        }
    }
}
