/**
 * DTO - Data Transfer Object (Like a simple customer ticket)
 *
 * Purpose:
 * Used to transfer data between layers, especially between
 * the Controller and the outside world (client).
 *
 * Technical:
 * Contains only the required fields needed for input/output.
 * Does NOT contain business logic or database-related details.
 *
 * Communication:
 * - Used by Controller and Service layers
 *
 * Real-life example:
 * The customer only provides necessary information:
 * - product name
 * - quantity
 *
 * It does not include internal details like database ID,
 * timestamps, or system status.
 */

package com.erick.spring.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private Integer id;
    @NotEmpty
    @Size(max = 50, min = 3)
    private String name;
    @NotNull
    @DecimalMax("10000")
    @PositiveOrZero
    private Double price;
    @NotEmpty
    @Size(max = 100, min = 10)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
