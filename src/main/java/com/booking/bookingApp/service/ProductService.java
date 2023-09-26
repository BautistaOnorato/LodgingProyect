package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.dto.ProductDto;
import com.booking.bookingApp.entity.*;
import com.booking.bookingApp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final SocialNetworkService socialNetworkService;
    private final RuleService ruleService;
    private final SecurityService securityService;

    public Product saveProduct(Product product) {
        for (Image image : product.getImages()) {
            image.setProduct(product);
        }
        for (Security security : product.getSecurities()) {
            security.setProduct(product);
        }
        for (Rule rule : product.getRules()) {
            rule.setProduct(product);
        }
        for (SocialNetwork socialNetworks : product.getSocialNetworks()) {
            socialNetworks.setProduct(product);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        if (productRepository.findById(product.getId()).isPresent()) {
            imageService.saveImages(product.getImages(), product);
            socialNetworkService.saveSocialNetworks(product.getSocialNetworks(), product);
            ruleService.saveRules(product.getRules(), product);
            securityService.saveSecurities(product.getSecurities(), product);
            return productRepository.save(product);
        } else return null;
    }

    public ProductDto findProductById(Long id) {
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()) {
            Product product = response.get();
            return productToProductDto(product);
        } else return null;
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(productToProductDto(product));
        }
        return productDtos;
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }
    }

    private ProductDto productToProductDto(Product product) {
        Set<ProductDto.ProductReservationDto> productReservationDtos = new HashSet<>();
        for (Reservation reservation : product.getReservations()) {
            productReservationDtos.add(new ProductDto.ProductReservationDto(reservation.getId(), reservation.getInitialDate(), reservation.getFinalDate()));
        }
        ProductDto productDto =
                new ProductDto(
                        product.getId(),
                        product.getTitle(),
                        product.getRating(),
                        product.getDescription(),
                        product.getCancellationPolicy(),
                        product.getLocation(),
                        product.getCategory(),
                        product.getSecurities(),
                        product.getRules(),
                        productReservationDtos,
                        product.getSocialNetworks(),
                        product.getImages(),
                        product.getCharacteristics()
                );
        return productDto;
    }
}
