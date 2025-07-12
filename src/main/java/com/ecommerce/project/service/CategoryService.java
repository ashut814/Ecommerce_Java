package com.ecommerce.project.service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

//    List<Category> getAllCategories();

    CategoryResponse getAllCategories();
//    String createCategory(Category category);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    String deleteCategory(Long categoryId) throws ResourceNotFoundException;

    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
