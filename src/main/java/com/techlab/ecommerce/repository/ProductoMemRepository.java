package com.techlab.ecommerce.repository;

import com.techlab.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("ProductoMemRepository")
public class ProductoMemRepository implements ProductRepository {

    private static Long nextId = 1L;
    ArrayList<Product> productos;

    public ProductoMemRepository() {

        this.productos = obtenerProductosTecnologicos();
    }

    public Product guardarProducto(Product producto) {
        this.updateId(producto);
        productos.add(producto);
        return producto;
    }

    public ArrayList<Product> obtenerProductos() {
        return this.productos;
    }

    public List<Product> obtenerProductosPorNombreYPrecio(String nombre, Double precioTope) {
        ArrayList<Product> productosEncontrados = new ArrayList<>();
        for (Product producto : this.productos) {
            if (estaIncluido(producto.getNombre(), nombre) && producto.getPrecio() <= precioTope) {
                productosEncontrados.add(producto);
            }
        }

        return productosEncontrados;
    }

    public ArrayList<Product> obtenerProductosPorNombre(String nombre) {
        ArrayList<Product> productosEncontrados = new ArrayList<>();

        for (Product producto : this.productos) {
            if (estaIncluido(producto.getNombre(), nombre)) {
                productosEncontrados.add(producto);
            }
        }

        return productosEncontrados;
    }

    public ArrayList<Product> obtenerProductosPorPrecio(Double precioTope) {
        ArrayList<Product> productosFiltrados = new ArrayList<>();

        for (Product producto : productos) {
            if (producto.getPrecio() <= precioTope) {
                productosFiltrados.add(producto);
            }
        }

        return productosFiltrados;
    }

    public Optional<Product> buscarProductoPorId(Long id) {
        for (Product producto : productos) {
            if (Objects.equals(producto.getId(), id)) {
                return Optional.of(producto);
            }
        }

        return Optional.empty();
    }

    public void actualizarProducto(Product producto) {
        System.out.println("Se actualizó el producto on id: " + producto.getId());
    }

    private boolean estaIncluido(String nombreCompleto, String nombreParcial) {
        String nombreCompletoFormateado = formatoBusqueda(nombreCompleto);

        return nombreCompletoFormateado.contains(formatoBusqueda(nombreParcial));
    }

    private String formatoBusqueda(String texto) {
        return texto.trim().toLowerCase();
    }

    public void borrarProducto(Product producto) {
        this.productos.remove(producto);
        //return producto;
    }

    private ArrayList<Product> obtenerProductosTecnologicos() {
        ArrayList<Product> productos = new ArrayList<>();

        productos.add(new Product(
                "Laptop Lenovo ThinkPad X1 Carbon",
                1899.99,
                "Ultrabook empresarial de alto rendimiento con diseño liviano y duradero.",
                "Computadoras"));

        productos.add(new Product(
                "Mouse inalámbrico Logitech MX Master 3",
                99.99,
                "Mouse ergonómico con múltiples botones programables y conectividad Bluetooth.",
                "Accesorios"));

        productos.add(new Product(
                "Teclado mecánico Razer BlackWidow V4",
                179.99,
                "Teclado gaming mecánico con retroiluminación RGB y teclas programables.",
                "Periféricos"));

        productos.add(new Product(
                "Monitor LG UltraWide 34 pulgadas",
                499.99,
                "Monitor panorámico con resolución QHD ideal para multitarea y productividad.",
                "Monitores"));

        productos.add(new Product(
                "Smartphone Samsung Galaxy S23 Ultra",
                1199.99,
                "Teléfono inteligente de gama alta con cámara de 200 MP y pantalla AMOLED.",
                "Smartphones"));

        productos.add(new Product(
                "Tablet Apple iPad Pro 12.9\"",
                1399.99,
                "Tableta potente con chip M2 y pantalla Liquid Retina XDR.",
                "Tablets"));

        productos.add(new Product(
                "Disco duro externo Seagate 2TB",
                79.99,
                "Almacenamiento portátil confiable con conectividad USB 3.0.",
                "Almacenamiento"));

        productos.add(new Product(
                "Memoria RAM Corsair Vengeance 16GB",
                69.99,
                "Módulo de memoria DDR4 ideal para gamers y entusiastas del rendimiento.",
                "Componentes"));

        productos.add(new Product(
                "Cargador inalámbrico Belkin Boost Up",
                39.99,
                "Base de carga inalámbrica rápida compatible con dispositivos Qi.",
                "Accesorios"));

        productos.add(new Product(
                "Auriculares Bluetooth Sony WH-1000XM5",
                349.99,
                "Auriculares con cancelación activa de ruido y audio de alta resolución.",
                "Audio"));

        for (Product producto : productos) {
            this.updateId(producto);
        }

        return productos;
    }

    private void updateId(Product producto) {
        producto.setId(nextId);
        nextId++;
    }
}
