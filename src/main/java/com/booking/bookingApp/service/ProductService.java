package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.*;
import com.booking.bookingApp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Product findProductById(Long id) {
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }
    }
}
