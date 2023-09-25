package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Image;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void removeImage(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            optionalImage.get().getProduct().removeImage(optionalImage.get());
            imageRepository.deleteById(id);
        }
    }

    public void saveImages(Set<Image> images, Product product) {
        if (images != null) {
            for (Image image : images) {
                image.setProduct(product);
                imageRepository.save(image);
            }
        }
    }
}
