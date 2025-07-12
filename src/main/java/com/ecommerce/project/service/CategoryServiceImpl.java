package com.ecommerce.project.service;

import com.ecommerce.project.exception.ApiException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositiories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ecommerce.project.exception.ResourceNotFoundException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

//    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new ApiException("No categories found");
        }
        return categories;
    }

    @Override
    public String createCategory(Category category) {
//        category.setCategoryId(nextId++);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new ApiException("Category already exists");
        } else{
            categoryRepository.save(category);
            return "Category added";
        }
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
            return "Category deleted";
        } else {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();
            existingCategory.setCategoryName(category.getCategoryName());
            categoryRepository.save(existingCategory);
            return "Category updated";
        } else {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
    }
}
