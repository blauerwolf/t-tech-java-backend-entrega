package com.techlab.ecommerce.repository;

import com.techlab.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> searchByNombre(String nombre);
    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByPrecioLessThanEqual(Double precio);
    List<Producto> findByNombreContainingAndPrecioLesThanEqual(String nombre, Double precio);
}
