/**
 * SERVICE INTERFACE (Like a contract of business operations)
 *
 * Purpose:
 * Defines what operations are available in the application.
 *
 * Technical:
 * Acts as a contract that the implementation must follow.
 * It does not contain logic, only method definitions.
 *
 * Communication:
 * - Used by Controller
 * - Implemented by Service Implementation
 *
 * Real-life example:
 * A list of what the store manager can do:
 * - sell product
 * - update product
 * - delete product
 *
 * But it does not explain how these actions are performed.
 */

package com.erick.spring.service;

import com.erick.spring.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO getProductById(Integer productId);
    //ProductDTO getProdructByName(String name);
    ProductDTO updateProduct(ProductDTO productDTO, Integer productId);
    void deleteProduct(Integer productId);
    List<ProductDTO> getAllProducts();

}
