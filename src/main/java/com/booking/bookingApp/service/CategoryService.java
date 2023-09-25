package com.booking.bookingApp.service;

import com.booking.bookingApp.entity.Category;
import com.booking.bookingApp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            return categoryRepository.save(category);
        } else return null;
    }

    public Category findCategoryById(Long id) {
        Optional<Category> response = categoryRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else return null;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
