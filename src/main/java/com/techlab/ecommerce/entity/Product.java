package com.techlab.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private Double price;
    private Integer stock;
    private String category;
    private String description;


    public String toString() {
        return "Nombre: " + getName() +
                " Precio: " + getPrice() +
                " Descripción: " + getDescription() +
                " Categoría: " + getCategory() +
                " Imagen: " + getImage();
    }
}
