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
 * - Receives requests from the client (frontend/user)
 * - Communicates with the Service layer
 *
 * Real-life example:
 * A customer talks to the cashier and says what they want.
 * The cashier does not make decisions, just forwards the request
 * to the store manager (Service) and returns the result.
 */

package com.erick.spring.controller;

import com.erick.spring.dto.ProductDTO;
import com.erick.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }


}