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
