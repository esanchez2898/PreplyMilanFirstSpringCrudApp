/**
 * CONTROLLER (Like a cashier in a store)
 *
 * Purpose:
 * Handles incoming HTTP requests and sends responses back to the client.
 * It acts as the entry point of the application.
 *
 * Technical:
 * Receives data (usually DTOs), calls the Service layer,
 * and returns the result (DTO) as a response.
 *
 * Communication:
 * - Receives requests from the client (frontend/Postman)
 * - Communicates with the Service layer
 *
 * Real-life example:
 * A customer talks to the cashier and says what they want.
 * The cashier does not make decisions, just forwards the request
 * to the store manager (Service) and returns the result.
 *
 * Annotations summary:
 * @RestController   → Marks this class as a REST controller (returns JSON, not HTML pages)
 * @RequestMapping   → All endpoints in this class will start with "/api/products"
 * @RequiredArgsConstructor → Lombok generates the constructor automatically for
 *                            all "final" fields (constructor injection, preferred over @Autowired)
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

    // Spring automatically injects a ProductService instance here (constructor injection).
    // "final" ensures this field is set once and never changed.

    // Why not @Autowired on the field?
    // With @Autowired, errors appear at runtime (when someone calls the endpoint).
    // With constructor injection, errors appear at startup — much easier to catch.
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Integer productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    // POST /api/products
    // Receives a JSON body, validates it, and saves a new product.
    @PostMapping
    public ResponseEntity<String> addProduct(
            @Validated @RequestBody ProductDTO productDTO,
            // @RequestBody  → converts the incoming JSON into a ProductDTO object
            // @Validated    → triggers the validation rules defined in ProductDTO
            //                 (@NotEmpty, @Size, @DecimalMax, etc.)
            Errors errors
            // Errors        → Spring stores the result of @Validated here.
            //                 If any rule failed, errors.hasErrors() returns true.
    ) {

        // If any validation rule from ProductDTO failed, we stop here.
        // We throw a custom exception instead of saving invalid data to the database.
        //
        // Note: errors.getFieldErrors() contains the specific messages from each
        // annotation (e.g. "name: must not be empty"), but right now we throw a
        // generic message. You could improve this later by reading those messages.
        if (errors.hasErrors()) {
            throw new DataNotValidateException("Product validation failed");
        }

        productService.addProduct(productDTO);
        return new ResponseEntity<>("Product was added to the db", HttpStatus.CREATED);
    }
}