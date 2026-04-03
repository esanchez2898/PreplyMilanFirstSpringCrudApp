/**
 * REPOSITORY (Like a warehouse or storage system)
 *
 * Purpose:
 * Handles all interactions with the database.
 *
 * Technical:
 * Provides CRUD operations (Create, Read, Update, Delete)
 * using frameworks like Spring Data JPA.
 *
 * Communication:
 * - Used by Service layer
 * - Works with Entity objects
 *
 * Real-life example:
 * The manager says:
 * - "save this product"
 * - "find this product"
 *
 * The repository executes those operations in the database.
 *
 * It does NOT contain business logic.
 */

package com.erick.spring.repository;

import com.erick.spring.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Optional<ProductEntity> findByName(String name);


}
