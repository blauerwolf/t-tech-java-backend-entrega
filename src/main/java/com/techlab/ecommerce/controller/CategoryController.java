package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.entity.Category;
import com.techlab.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category create(@RequestBody @Valid Category c) {
        return categoryService.create(c);
    }

    @GetMapping
    public List<Category> list() {
        return categoryService.list();
    }
}
