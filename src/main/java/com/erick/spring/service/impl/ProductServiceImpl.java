/**
 * SERVICE IMPLEMENTATION (Like the store manager)
 * <p>
 * 🎯 Purpose:
 * Contains the business logic of the application.
 * This class implements the rules and processes that define
 * how the system behaves.
 * <p>
 * ⚙️ Technical:
 * - Implements the ProductService interface (the service contract).
 * - Applies business rules before interacting with the database.
 * - Coordinates between Repository (data access) and Converter (DTO ↔ Entity).
 * - Marked with @Service so Spring can detect and manage it as a bean.
 * - Often uses @Transactional to manage database transactions.
 * <p>
 * 🔄 Communication:
 * - Called by the Controller layer
 * - Uses Repository to read/write data from the database
 * - Uses Converter to transform DTO ↔ Entity objects
 * <p>
 * 🧩 Interface-based design:
 * The Controller depends on the ProductService interface,
 * not directly on this implementation.
 * <p>
 * This allows the system to have multiple implementations
 * of the same service if needed.
 * <p>
 * Example:
 * - ProductServiceImpl → normal database operations
 * - ProductServiceCacheImpl → cached version for faster reads
 * <p>
 * Spring automatically injects the correct implementation
 * using dependency injection.
 * <p>
 * If multiple implementations exist, Spring can decide which one
 * to use through annotations such as:
 * - @Primary (default implementation)
 * - @Qualifier (explicit selection)
 * <p>
 * 🧾 Real-life example:
 * The store manager makes decisions such as:
 * - checking if a product already exists
 * - validating if an operation is allowed
 * <p>
 * Then the manager:
 * - asks the warehouse (Repository) to store or retrieve data
 * - uses a translator (Converter) to convert between internal
 * system objects and customer-facing data.
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

    private final ProductRepository productRepository; // DATABASE
    private final ProductConverter productConverter;   //  -> ENTITY <--> DTO <-


// --- GET ---

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {

        List<ProductDTO> productsDTO = new ArrayList<>();
        List<ProductEntity> productsEntity = productRepository.findAll(); // JpaRepository

        for (ProductEntity product : productsEntity) {
            productsDTO.add(productConverter.entityToDto(product));
        }

        return productsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer productId) {

        ProductDTO productDTO = null;
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            productDTO = productConverter.entityToDto(productEntity);
        } else {
            throw new InstanceUndefinedException("The product with id " + productId + " has not been found");
        }

        return productDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductByName(String productName) {

        ProductEntity productEntity = productRepository.findByName(productName)
                .orElseThrow(() ->
                        new InstanceUndefinedException("The product with name " + productName + " has not been found")
                );

        return productConverter.entityToDto(productEntity);
    }



// --- ADD ---

    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {

        if (productRepository.findByName(productDTO.getName()).isPresent()) throw new ExistingInstanceException("Product with this name already exists in DB");

        // 🔄 Convert DTO → Entity and save it
        ProductEntity savedProduct = productRepository.save(productConverter.dtoToEntity(productDTO));

        // 🔁 Convert back Entity → DTO for response
        return productConverter.entityToDto(savedProduct);
    }



// --- UPDATE ---

    @Override
    @Transactional
    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {

        getProductById(productId);

        // Set the ID coming from the URL into the DTO. This is crucial because JPA uses the ID to determine
        // whether to perform an UPDATE or an INSERT. If the ID is null, a new record would be created instead.
        productDTO.setId(productId);
        ProductEntity productEntity = productRepository.save(productConverter.dtoToEntity(productDTO));

        return productConverter.entityToDto(productEntity);
    }


// --- DELETE ---
    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        getProductById(productId);
        productRepository.deleteById(productId);
    }


}
