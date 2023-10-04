package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Category;
import com.booking.bookingApp.exception.BadRequestException;
import com.booking.bookingApp.exception.ResourceNotFoundException;
import com.booking.bookingApp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category) throws BadRequestException {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new BadRequestException("Something went wrong. The Category was not created");
        }
    }

    public Category updateCategory(Category category) throws ResourceNotFoundException {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            return categoryRepository.save(category);
        } else throw new ResourceNotFoundException("Something went wrong. The category with id: " + category.getId() + " does not exist.");
    }

    public Category findCategoryById(Long id) throws ResourceNotFoundException {
        Optional<Category> response = categoryRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else throw new ResourceNotFoundException("Something went wrong. The category with id: " + id + " does not exist.");
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) throws ResourceNotFoundException {
        Optional<Category> response = categoryRepository.findById(id);
        if (response.isPresent()) {
            categoryRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Something went wrong. The category with id: " + id + " does not exist.");
    }
}
