package com.techlab.ecommerce.service;

import com.techlab.ecommerce.dto.request.CartItemRequestDTO;
import com.techlab.ecommerce.dto.response.CartItemResponseDTO;
import com.techlab.ecommerce.dto.response.CartResponseDTO;
import com.techlab.ecommerce.entity.Cart;
import com.techlab.ecommerce.entity.CartItem;
import com.techlab.ecommerce.entity.Product;
import com.techlab.ecommerce.exception.ProductNotFoundException;
import com.techlab.ecommerce.repository.CartItemRepository;
import com.techlab.ecommerce.repository.CartRepository;
import com.techlab.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;

    public CartResponseDTO getCart(Long cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return mapCartToDTO(cart);
    }

    public CartResponseDTO addItem(Long cartId, CartItemRequestDTO dto) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = itemRepo.findByCartIdAndProductId(cartId, dto.getProductId())
                .orElse(new CartItem(null, product, 0, cart));

        item.setQuantity(item.getQuantity() + dto.getQuantity());
        itemRepo.save(item);

        return mapCartToDTO(cart);
    }

    public CartResponseDTO updateItem(Long cartId, Long itemId, Integer quantity) {

        CartItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getId().equals(cartId))
            throw new RuntimeException("Item does not belong to cart");

        item.setQuantity(quantity);
        itemRepo.save(item);

        return getCart(cartId);
    }

    public CartResponseDTO deleteItem(Long cartId, Long itemId) {
        CartItem item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        itemRepo.delete(item);

        return getCart(cartId);
    }

    // -----------------------------
    // Mapping helpers
    // -----------------------------
    private CartResponseDTO mapCartToDTO(Cart cart) {
        List<CartItemResponseDTO> dtos = cart.getItems()
                .stream()
                .map(i -> new CartItemResponseDTO(
                        i.getId(),
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getProduct().getPrice(),
                        i.getProduct().getPrice() * i.getQuantity()
                )).toList();

        double total = dtos.stream()
                .mapToDouble(CartItemResponseDTO::getSubtotal)
                .sum();

        return new CartResponseDTO(cart.getId(), dtos, total);
    }
}