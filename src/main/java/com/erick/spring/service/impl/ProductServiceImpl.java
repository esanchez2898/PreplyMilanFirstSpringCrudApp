/**
 * SERVICE IMPLEMENTATION (Like the store manager)
 *
 * Purpose:
 * Contains the business logic of the application.
 *
 * Technical:
 * Processes data, applies rules, and coordinates between
 * Repository and Converter layers.
 *
 * Communication:
 * - Called by Controller
 * - Uses Repository (data access)
 * - Uses Converter (DTO ↔ Entity transformation)
 *
 * Real-life example:
 * The manager decides:
 * - if there is enough stock
 * - if the order can be processed
 *
 * It also asks the warehouse (Repository) to store or retrieve data,
 * and uses a translator (Converter) to understand the customer request.
 */

package com.erick.spring.service.impl;

import com.erick.spring.converter.ProductConverter;
import com.erick.spring.dto.ProductDTO;
import com.erick.spring.entity.ProductEntity;
import com.erick.spring.repository.ProductRepository;
import com.erick.spring.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        ProductEntity savedProduct = productRepository.save(productConverter.dtoToEntity(productDTO));
        return productConverter.entityToDto(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Integer productId) {
        return null;
    }

    @Override
    public void deleteProduct(Integer productId) {

    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return List.of();
    }
}
