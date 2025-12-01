package com.techlab.ecommerce.controller;

import java.util.List;

import com.techlab.ecommerce.dto.request.ProductRequestDTO;
import com.techlab.ecommerce.dto.response.ProductResponseDTO;
import com.techlab.ecommerce.entity.Product;
import com.techlab.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "${frontend.url}")
public class ProductController {

    private ProductService service;

    // Inyección de dependencia
    public ProductController(ProductService service) {

        this.service = service;
    }

    // @Operations sirve para generar documentación en swagger
    @Operation(summary = "Crear producto", description = "Agrego un nuevo producto al sistema")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @PostMapping("")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProduct(requestDTO));
    }

    @Operation(summary = "Listar productos", description = "Obtiene un listado de todos los productos del sistema")
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProductsByName(@RequestParam String queryName) {
        return this.service.searchProductByName(queryName);
    }

    @Operation(summary = "Buscar por ID", description = "Obtiene un producto que coincida con el ID")
    @GetMapping("/{id}")
    public ProductResponseDTO searchProductById(@PathVariable Long id) {
        return this.service.searchProductById(id);
    }

    @Operation(summary = "Actalizar producto", description = "Actualiza un producto usando las propiedades de la requesto, esto pasa si el ID es válido.")
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
