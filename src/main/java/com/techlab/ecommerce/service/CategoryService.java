package com.techlab.ecommerce.service;

import com.techlab.ecommerce.entity.Category;
import com.techlab.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category c) {
        return categoryRepository.save(c);
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
