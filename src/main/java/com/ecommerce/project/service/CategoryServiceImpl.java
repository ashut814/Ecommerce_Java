package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
        return "Category added";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if (categoryOptional.isPresent()) {
            categories.remove(categoryOptional.get());
            return "Category deleted";
        } else {
            return "Category not found";
        }
    }
}
