/**
 * DTO - Data Transfer Object (Like a simple customer ticket)
 *
 * 🎯 Purpose:
 * This class is used to transfer data between the Controller and the client (API requests/responses).
 * It defines what data is exposed to the outside world.
 *
 * ⚙️ Technical:
 * - Contains only the necessary fields (no business logic).
 * - Does NOT represent the database structure directly.
 * - Used for validation using annotations (e.g., @NotBlank, @Size, etc.).
 * - Serializable → allows this object to be converted into a stream (useful for APIs, caching, etc.).
 *
 * 🔄 Communication:
 * - Controller ↔ DTO ↔ Client (JSON)
 * - Service can also use DTOs to avoid exposing Entities directly.
 *
 * 🧠 Why not use Entity instead?
 * - Entities may contain sensitive/internal data (e.g., IDs, timestamps).
 * - DTOs give full control over what is sent/received.
 *
 * 🧾 Real-life example:
 * A customer ticket only includes what the customer provides:
 * - name
 * - price
 * - description
 *
 * It does NOT include internal system data like:
 * - database ID (optional)
 * - creation date
 * - system flags
 */

package com.erick.spring.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private Integer id;
    @NotBlank(message = "Name cannot be null, empty, or only spaces")
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
