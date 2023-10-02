package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Image;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void removeImages(Set<Image> oldImages, Set<Image> newImages) {
        List<Image> deleteImages = new ArrayList<>();
        for (Image oldImage : oldImages) {
            boolean find = false;
            for (Image newImage : newImages) {
                if (Objects.equals(oldImage.getId(), newImage.getId())) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                deleteImages.add(oldImage);
            }
        }
        imageRepository.deleteAll(deleteImages);
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
