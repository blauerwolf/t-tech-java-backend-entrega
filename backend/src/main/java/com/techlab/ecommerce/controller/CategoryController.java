package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.entity.Category;
import com.techlab.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría para productos")
    public Category create(@RequestBody @Valid Category c) {
        return categoryService.create(c);
    }

    @GetMapping
    @Operation(summary ="Listar categorías", description = "Lista todas las categorías en el sistema")
    public List<Category> list() {
        return categoryService.list();
    }
}
