package com.booking.bookingApp.controller;

import com.booking.bookingApp.dto.ProductDto;
import com.booking.bookingApp.dto.ShortProductDto;
import com.booking.bookingApp.entity.Product;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<Product> postProduct(@RequestBody Product product) throws BadRequestException {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PreAuthorize("hasAuthority('admin:update')")
    @PatchMapping
    public ResponseEntity<Product> putProduct(@RequestBody Product product) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + " has been deleted.");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ShortProductDto>> searchProducts(
            @RequestParam(name = "category", required = false) String categoryId,
            @RequestParam(name = "city", required = false) String cityId,
            @RequestParam(name = "initial_date", required = false) String initialDate,
            @RequestParam(name = "final_date", required = false) String finalDate
    ) {
        return ResponseEntity.ok(productService.findProductByFilter(categoryId, cityId, initialDate, finalDate));
    }
}
