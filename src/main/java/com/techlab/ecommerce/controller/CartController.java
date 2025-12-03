package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.request.CartItemRequestDTO;
import com.techlab.ecommerce.dto.response.CartResponseDTO;
import com.techlab.ecommerce.entity.Cart;
import com.techlab.ecommerce.repository.CartRepository;
import com.techlab.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "${frontend.url}")
public class CartController {

    private final CartService service;
    private final CartRepository cartRepo;

    @PostMapping("")
    @Operation(summary = "Crear carrito de compras", description = "Crea un nuevo carrito de compras vacío con saldo total 0.0")
    public CartResponseDTO createCart() {
        Cart cart = cartRepo.save(new Cart());
        return service.getCart(cart.getId());
    }

    @GetMapping("")
    @Operation(summary = "Listar todos los carritos", description = "Devuelve una lista con todos los carritos del sistema")
    public List<CartResponseDTO> getAllCarts() {
        return this.service.getAllCarts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener carrito por ID", description = "Obtiene el carrito de compras a partir de su ID")
    public CartResponseDTO getCart(@PathVariable Long id) {
        return service.getCart(id);
    }

    @PostMapping("/{id}/items")
    @Operation(summary = "Agregar ítem", description = "Agrega un producto al carrito de compras")
    public CartResponseDTO addItem(@PathVariable Long id, @RequestBody CartItemRequestDTO dto) {
        return service.addItem(id, dto);
    }

    @PutMapping("/{id}/items/{itemId}")
    @Operation(summary = "Actualizar ítem", description = "Actualiza un producto del carrito de compras")
    public CartResponseDTO updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return service.updateItem(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    @Operation(summary = "Eliminar ítem", description = "Elimina un producto del carrito de compras")
    public CartResponseDTO deleteItem(
            @PathVariable Long id,
            @PathVariable Long itemId) {
        return service.deleteItem(id, itemId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar carrito", description = "Elimina un carrito de compras si está vacío")
    public ResponseEntity<?> deleteEmptyCart(@PathVariable Long id) {
        this.service.deleteEmptyCart(id);
        return ResponseEntity.ok().body(Map.of(
                "status", "success",
                "message", "Carrito eliminado correctamente"
        ));
    }
}
