package com.techlab.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CartItemRequestDTO {
    Long productId;
    Integer quantity;
}
