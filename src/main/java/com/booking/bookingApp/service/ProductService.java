package com.booking.bookingApp.service;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.dto.ProductDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.entity.*;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.*;
import com.booking.bookingApp.utils.Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final SocialNetworkService socialNetworkService;
    private final RuleService ruleService;
    private final SecurityService securityService;

    public Product saveProduct(Product product) throws BadRequestException {
        try {
            for (Image image : product.getImages()) {
                image.setProduct(product);
            }
            for (Security security : product.getSecurities()) {
                security.setProduct(product);
            }
            for (Rule rule : product.getRules()) {
                rule.setProduct(product);
            }
            for (SocialNetwork socialNetwork : product.getSocialNetworks()) {
                socialNetwork.setProduct(product);
                if (socialNetwork.getTitle().equals(SocialNetworkEnum.X)) {
                    socialNetwork.setIcon("https://booking-app-bautista-onorato.s3.sa-east-1.amazonaws.com/Icons/brand-x.svg");
                } else if (socialNetwork.getTitle().equals(SocialNetworkEnum.FACEBOOK)) {
                    socialNetwork.setIcon("https://booking-app-bautista-onorato.s3.sa-east-1.amazonaws.com/Icons/facebook.svg");
                } else socialNetwork.setIcon("https://booking-app-bautista-onorato.s3.sa-east-1.amazonaws.com/Icons/brand-instagram.svg");
            }
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Something went wrong. The product was not created.");
        }
    }

    @Transactional
    public Product updateProduct(Product product) throws ResourceNotFoundException {
        Optional<Product> oldProduct = productRepository.findById(product.getId());
        if (oldProduct.isPresent()) {
            imageService.saveImages(product.getImages(), product);
            imageService.removeImages(oldProduct.get().getImages(), product.getImages());
            socialNetworkService.saveSocialNetworks(product.getSocialNetworks(), product);
            socialNetworkService.removeSocialNetworks(oldProduct.get().getSocialNetworks(), product.getSocialNetworks());
            ruleService.saveRules(product.getRules(), product);
            ruleService.removeRules(oldProduct.get().getRules(), product.getRules());
            securityService.saveSecurities(product.getSecurities(), product);
            securityService.removeSecurities(oldProduct.get().getSecurities(), product.getSecurities());
            return productRepository.save(product);
        } else throw new ResourceNotFoundException("Something went wrong. The product with id: " + product.getId() + " does not exist.");
    }

    public ProductDto findProductById(Long id) throws ResourceNotFoundException {
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()) {
            Product product = response.get();
            return productToProductDto(product);
        } else throw new ResourceNotFoundException("Something went wrong. The product with id: " + id + " does not exist.");
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(productToProductDto(product));
        }
        return productDtos;
    }

    public List<ShortProductDto> findProductByFilter(String categoryId, String cityId, String initialDate, String finalDate) {
        List<ShortProductDto> response = new ArrayList<>();
        System.out.println(cityId);
        System.out.println(categoryId);
        System.out.println(initialDate);
        System.out.println(finalDate);
        if (cityId != null || categoryId != null || (initialDate != null && finalDate != null)) {
            for (Product product : productRepository.filterProducts(Util.nullToString(cityId), Util.nullToString(categoryId), Util.nullToString(initialDate), Util.nullToString(finalDate))) {
                response.add(productToShortProductDto(product));
            }
        } else {
            System.out.println(2);
            for (Product product : productRepository.findXRandomProducts(12)) {
                response.add(productToShortProductDto(product));
            }
        }
        return response;
    }

    public void deleteProduct(Long id) throws ResourceNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The product with id: " + id + " does not exist.");
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

    private ShortProductDto productToShortProductDto(Product product) {
        return new ShortProductDto(
                product.getId(),
                product.getTitle(),
                product.getLocation(),
                product.getImages(),
                product.getRating()
        );
    }
}
