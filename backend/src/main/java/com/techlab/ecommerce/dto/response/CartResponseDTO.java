package com.techlab.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private List<CartItemResponseDTO> items;
    private Double total;
}
