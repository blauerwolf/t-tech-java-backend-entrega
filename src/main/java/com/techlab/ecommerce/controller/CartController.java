package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.request.CartItemRequestDTO;
import com.techlab.ecommerce.dto.response.CartResponseDTO;
import com.techlab.ecommerce.entity.Cart;
import com.techlab.ecommerce.repository.CartRepository;
import com.techlab.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "${frontend.url}")
public class CartController {

    private final CartService service;
    private final CartRepository cartRepo;

    @PostMapping("")
    public CartResponseDTO createCart() {
        Cart cart = cartRepo.save(new Cart());
        return service.getCart(cart.getId());
    }

    @GetMapping("/{id}")
    public CartResponseDTO getCart(@PathVariable Long id) {
        return service.getCart(id);
    }

    @PostMapping("/{id}/items")
    public CartResponseDTO addItem(@PathVariable Long id, @RequestBody CartItemRequestDTO dto) {
        return service.addItem(id, dto);
    }

    @PutMapping("/{id}/items/{itemId}")
    public CartResponseDTO updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return service.updateItem(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public CartResponseDTO deleteItem(
            @PathVariable Long id,
            @PathVariable Long itemId) {
        return service.deleteItem(id, itemId);
    }
}
