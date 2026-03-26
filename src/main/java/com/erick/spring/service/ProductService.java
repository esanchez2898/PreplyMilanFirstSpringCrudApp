package com.erick.spring.service;

import com.erick.spring.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO getProductById(Integer productId);
    ProductDTO updateProduct(ProductDTO productDTO, Integer productId);
    void deleteProduct(Integer productId);
    List<ProductDTO> getAllProducts();

}
