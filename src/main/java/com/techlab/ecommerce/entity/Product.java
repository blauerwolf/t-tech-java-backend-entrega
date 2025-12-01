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

    private String nombre;
    private double precio;
    private String descripcion;
    private String categoria;

    public Product(String nuevoNombre, double nuevoPrecio, String nuevaDescripcion, String nuevaCategoria) {
        nombre = nuevoNombre;
        precio = nuevoPrecio;
        descripcion = nuevaDescripcion;
        categoria = nuevaCategoria;
    }

    public Product() { }

    public String toString() {
        return "Nombre: " + getNombre() +
                " Precio: " + getPrecio() +
                " Descripción: " + getDescripcion() +
                " Categoría: " + getCategoria();
    }
}
