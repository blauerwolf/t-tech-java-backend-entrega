package com.techlab.ecommerce.service;

import java.util.List;
import com.techlab.ecommerce.dto.request.ProductRequestDTO;
import com.techlab.ecommerce.dto.response.ProductResponseDTO;
import com.techlab.ecommerce.entity.Category;
import com.techlab.ecommerce.entity.Product;
import com.techlab.ecommerce.exception.ProductNotFoundException;
import com.techlab.ecommerce.exception.TechlabException;
import com.techlab.ecommerce.repository.CategoryRepository;
import com.techlab.ecommerce.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    // DI
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        // 1) Validar categoría obligatoria
        if (dto.getCategoryId() == null) {
            throw new TechlabException(
                    "Categoría requerida",
                    "Debe indicar una categoría válida",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2) Buscar categoría en DB
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new TechlabException(
                        "Categoría inválida",
                        "La categoría con ID " + dto.getCategoryId() + " no existe",
                        HttpStatus.BAD_REQUEST
                ));

        // 3) Crear el producto
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);

        // 4) Setear la categoría
        product.setCategory(category);

        // 5) Guardar
        product = this.productRepository.save(product);

        // 6) Convertir a DTO
        ProductResponseDTO response = new ProductResponseDTO();
        BeanUtils.copyProperties(product, response);
        response.setCategoryId(category.getId());
        response.setCategoryName(category.getName());

        return response;
    }

    public List<ProductResponseDTO> getProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(this::mapperToDTO)
                .toList();
    }

    public List<ProductResponseDTO> searchProductByName(String queryName) {
        List<Product> foundProducts = this.productRepository.findByNameContainingIgnoreCase(queryName);

        if (foundProducts.isEmpty()) {
            throw new ProductNotFoundException(queryName);
        }

        return foundProducts
                .stream()
                .map(this::mapperToDTO)
                .toList();
    }

    public ProductResponseDTO searchProductById(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        return this.mapperToDTO(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto)  {

        // 1) Buscar producto existente
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        // 2) Validar categoría obligatoria
        if (dto.getCategoryId() == null) {
            throw new TechlabException(
                    "Categoría requerida",
                    "Debe indicar una categoría válida",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 3) Buscar categoría en DB
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new TechlabException(
                        "Categoría inválida",
                        "La categoría con ID " + dto.getCategoryId() + " no existe",
                        HttpStatus.BAD_REQUEST
                ));

        // 4) Copiar propiedades y actualizar categoría
        BeanUtils.copyProperties(dto, product);
        product.setCategory(category);

        // 5) Guardar
        product = this.productRepository.save(product);

        // 6) Convertir a respuesta
        return mapperToDTO(product);
    }

    public ProductResponseDTO deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        this.productRepository.delete(product);

        return this.mapperToDTO(product);
    }

    /**
     * Convertir la entidad de *Product* en un *ProductResponseDTO* para la respuesta
     *
     * @param p - producto a convertir
     * @return ProductResponseDTO
     */
    private ProductResponseDTO mapperToDTO(Product p) {
        ProductResponseDTO dto = new ProductResponseDTO();
        BeanUtils.copyProperties(p, dto);

        if (p.getCategory() != null) {
            dto.setCategoryId(p.getCategory().getId());
            dto.setCategoryName(p.getCategory().getName());
        }

        return dto;
    }
}