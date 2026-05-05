/**
 * CONTROLLER (Like a cashier in a store)
 * <p>
 * Purpose:
 * Handles incoming HTTP requests and sends responses back to the client.
 * It acts as the entry point of the application.
 * <p>
 * Technical:
 * Receives data (usually DTOs), calls the Service layer,
 * and returns the result (DTO) as a response.
 * <p>
 * Communication:
 * - Receives requests from the client (frontend/Postman)
 * - Communicates with the Service layer
 * <p>
 * Real-life example:
 * A customer talks to the cashier and says what they want.
 * The cashier does not make decisions, just forwards the request
 * to the store manager (Service) and returns the result.
 * <p>
 * Annotations summary:
 *
 * @RestController → Marks this class as a REST controller (returns JSON, not HTML pages)
 * @RequestMapping → All endpoints in this class will start with "/api/products"
 * @RequiredArgsConstructor → Lombok generates the constructor automatically for
 * all "final" fields (constructor injection, preferred over @Autowired)
 */

package com.erick.spring.controller;

import com.erick.spring.dto.ProductDTO;
import com.erick.spring.exception.DataNotValidateException;
import com.erick.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
@RequiredArgsConstructor // (Lombok) generates the constructor for us, so we don't write it manually.
public class ProductController {

    private final ProductService productService;


// GET REQUEST
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Integer productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable("name") String productName) {
        return new ResponseEntity<>(productService.getProductByName(productName), HttpStatus.OK);
    }


// POST REQUEST
// Receives a JSON body, validates it, and saves a new product.
    @PostMapping
    public ResponseEntity<String> addProduct(@Validated @RequestBody ProductDTO productDTO, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidateException("Product validation failed");
        }

        productService.addProduct(productDTO); // <-- the error message is in ProductServiceImpl
        return new ResponseEntity<>("Product was added to the db", HttpStatus.CREATED);
    }


// PUT REQUEST
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Integer productId, @Validated @RequestBody ProductDTO productDTO, Errors errors) {

        if (errors.hasErrors()) {
            throw new DataNotValidateException("Product validation failed");
        }

        productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>("Product with id " + productId + " was updated", HttpStatus.OK);
    }


// DELETE REQUEST
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product with id " + productId + " was deleted", HttpStatus.OK);
    }

}