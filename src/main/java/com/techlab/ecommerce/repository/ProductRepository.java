package com.techlab.ecommerce.repository;

import java.util.List;

import com.techlab.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    Product guardarProducto(Product producto);

    /*
    void borrarProducto(Producto producto);
    Optional<Producto> buscarProductoPorId(Long id);
    List<Producto> obtenerProductos();

    List<Producto> obtenerProductosPorPrecio(Double precio);
    List<Producto> obtenerProductosPorNombreYPrecio(String nombre, Double precio);


    List<Product> searchByNombre(String nombre);
    Product findById(Double id);
    List<Product> findByNombreContaining(String nombre);
    List<Product> findByPrecioLessThanEqual(Double precio);
    List<Product> findByNombreContainingAndPrecioLessThanEqual(String nombre, Double precio);
    */
    List<Product> findByNameContainingIgnoreCase(String name);
}
