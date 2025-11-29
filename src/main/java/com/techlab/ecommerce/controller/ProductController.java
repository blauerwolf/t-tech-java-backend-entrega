package com.techlab.ecommerce.controller;

import java.util.List;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/products")
    public Producto crearProducto(@RequestBody Producto producto) {
        return this.service.crearProducto(producto);
    }

    // GET /products?nombre="product"&precio=123
    @GetMapping("/products")
    public List<Producto> listarProductos(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "0") Double precio) {
        return this.service.listarProductos(nombre, precio);
    }

    //@PatchMapping
    @PutMapping("/products/{id}")
    public Producto editarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return this.service.editarNombreProducto(id, producto);
    }

    @DeleteMapping("/products/{id}")
    public Producto borrarProducto(@PathVariable(name = "id") Long productId) {
        return this.service.borrarProducto(productId);
    }

}
