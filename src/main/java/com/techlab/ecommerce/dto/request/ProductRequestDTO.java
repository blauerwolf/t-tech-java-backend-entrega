package com.techlab.ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private String image;
    private Double price;
    private Integer stock;
    //private String category;
    Long categoryId;
    private String description;
}
