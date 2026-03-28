/**
 * ENTITY (Like the full system record)
 *
 * Purpose:
 * Represents a table in the database.
 *
 * Technical:
 * Annotated class (e.g., @Entity) mapped to a database table.
 * Each field corresponds to a column.
 *
 * Communication:
 * - Used by Repository
 * - Used by Service (indirectly via Converter)
 *
 * Real-life example:
 * This is the complete stored data:
 * - ID
 * - name
 * - price
 * - stock
 * - timestamps
 *
 * It contains all internal information of the system.
 */

package com.erick.spring.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "products")
public class ProductEntity implements Serializable { //json

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50, unique = true)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false, length = 100)
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
