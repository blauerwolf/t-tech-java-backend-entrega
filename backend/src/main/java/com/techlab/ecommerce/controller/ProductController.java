package com.techlab.ecommerce.controller;

import java.util.List;

import com.techlab.ecommerce.dto.request.ProductRequestDTO;
import com.techlab.ecommerce.dto.response.ProductResponseDTO;
import com.techlab.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "${frontend.url}")
public class ProductController {

    private ProductService service;

    // Inyección de dependencia
    public ProductController(ProductService service) {

        this.service = service;
    }

    // @Operations sirve para generar documentación en swagger
    @Operation(summary = "Crear producto", description = "Agrega un nuevo producto al sistema")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @PostMapping("")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProduct(requestDTO));
    }

    @Operation(summary = "Listar productos", description = "Lista todos los productos con filtros opcionales, paginación y ordenamiento")
    @GetMapping("")
    public ResponseEntity<Page<ProductResponseDTO>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,

            // filtros opcionales
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        Page<ProductResponseDTO> result = service.listProducts(
                page, size, sortBy, direction, categoryId, name, minPrice, maxPrice
        );

        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Buscar productos", description = "Obtiene un listado de todos los productos del sistema que cumplen con el término de búsqueda")
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProductsByName(@RequestParam String queryName) {
        return this.service.searchProductByName(queryName);
    }

    @Operation(summary = "Buscar por ID", description = "Obtiene un producto que coincida con el ID")
    @GetMapping("/{id}")
    public ProductResponseDTO searchProductById(@PathVariable Long id) {
        return this.service.searchProductById(id);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto usando las propiedades, esto pasa si el ID es válido.")
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id,
        @RequestBody ProductRequestDTO requestDTO) {
        return this.service.updateProduct(id, requestDTO);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema. Ésto pasa si el ID es válido")
    @DeleteMapping("/{id}")
    public ProductResponseDTO deleteProduct(@PathVariable Long id) {
        return this.service.deleteProduct(id);
    }
}
