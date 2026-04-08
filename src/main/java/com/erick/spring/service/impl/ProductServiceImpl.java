/**
 * SERVICE IMPLEMENTATION (Like the store manager)
 *
 * 🎯 Purpose:
 * Contains the business logic of the application.
 * This class implements the rules and processes that define
 * how the system behaves.
 *
 * ⚙️ Technical:
 * - Implements the ProductService interface (the service contract).
 * - Applies business rules before interacting with the database.
 * - Coordinates between Repository (data access) and Converter (DTO ↔ Entity).
 * - Marked with @Service so Spring can detect and manage it as a bean.
 * - Often uses @Transactional to manage database transactions.
 *
 * 🔄 Communication:
 * - Called by the Controller layer
 * - Uses Repository to read/write data from the database
 * - Uses Converter to transform DTO ↔ Entity objects
 *
 * 🧩 Interface-based design:
 * The Controller depends on the ProductService interface,
 * not directly on this implementation.
 *
 * This allows the system to have multiple implementations
 * of the same service if needed.
 *
 * Example:
 * - ProductServiceImpl → normal database operations
 * - ProductServiceCacheImpl → cached version for faster reads
 *
 * Spring automatically injects the correct implementation
 * using dependency injection.
 *
 * If multiple implementations exist, Spring can decide which one
 * to use through annotations such as:
 * - @Primary (default implementation)
 * - @Qualifier (explicit selection)
 *
 * 🧾 Real-life example:
 * The store manager makes decisions such as:
 * - checking if a product already exists
 * - validating if an operation is allowed
 *
 * Then the manager:
 * - asks the warehouse (Repository) to store or retrieve data
 * - uses a translator (Converter) to convert between internal
 *   system objects and customer-facing data.
 */

package com.erick.spring.service.impl;

import com.erick.spring.converter.ProductConverter;
import com.erick.spring.dto.ProductDTO;
import com.erick.spring.entity.ProductEntity;
import com.erick.spring.exception.ExistingInstanceException;
import com.erick.spring.exception.InstanceUndefinedException;
import com.erick.spring.repository.ProductRepository;
import com.erick.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

//    private ProductRepository productRepository;
//    private ProductConverter productConverter;
//
//    @Autowired
//    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter) {
//        this.productRepository = productRepository;
//        this.productConverter = productConverter;
//    }

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;


    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {

        Optional<ProductEntity> productEntityOptional = productRepository.findByName(productDTO.getName());

        if (productEntityOptional.isPresent()) {
            throw new ExistingInstanceException("Product with this name already exist in DB");
        }
        ProductEntity savedProduct = productRepository.save(productConverter.dtoToEntity(productDTO));
        return productConverter.entityToDto(savedProduct);
    }



    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer productId) {
        ProductDTO returnValue = null;

        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            returnValue = productConverter.entityToDto(productEntity);
        } else {
            throw new InstanceUndefinedException("The product with id " + productId + " has not been found");
        }

        return returnValue;
    }


    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Integer productId) {

        ProductDTO returnValue = null;
        getProductById(productId);

        productDTO.setId(productId);
        ProductEntity productEntity = productRepository.save(productConverter.dtoToEntity(productDTO));

        return productConverter.entityToDto(productEntity);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {

        List<ProductDTO> returnValue = new ArrayList<>();
        List<ProductEntity> allProducts = productRepository.findAll(); // JpaRepository

        for (ProductEntity product : allProducts) {
            returnValue.add(productConverter.entityToDto(product));
        }

        return returnValue;
    }
}
