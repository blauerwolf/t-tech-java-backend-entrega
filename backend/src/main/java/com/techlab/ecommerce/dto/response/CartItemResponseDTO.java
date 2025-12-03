package com.techlab.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponseDTO {
    Long id;
    Long productId;
    String productName;
    Integer quantity;
    Double price;
    Double subtotal;
}
