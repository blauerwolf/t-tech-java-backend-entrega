package com.techlab.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String image;
    private Double price;
    private Integer stock;

    @NotNull(message = "Debe indicar una categoría.")
    Long categoryId;
    private String description;
}
