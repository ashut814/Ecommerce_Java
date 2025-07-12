package com.ecommerce.project.service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    String createCategory(Category category);

    String deleteCategory(Long categoryId) throws ResourceNotFoundException;
    String updateCategory(Category category, Long categoryId);
}
