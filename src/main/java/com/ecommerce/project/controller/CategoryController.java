package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/public/categories")
    public String createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/public/categories/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
