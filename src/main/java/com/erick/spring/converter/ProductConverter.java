/**
 * CONVERTER (Like a translator between customer and system)
 *
 * Purpose:
 * Converts data between DTO and Entity.
 *
 * Technical:
 * Maps fields between DTO (external representation)
 * and Entity (internal database model).
 *
 * Communication:
 * - Used by Service layer
 *
 * Real-life example:
 * Customer says: "I want an iPhone" (DTO)
 *
 * But the system needs:
 * - internal ID
 * - status
 * - timestamps
 * - additional fields (Entity)
 *
 * So:
 * - Converts DTO → Entity (before saving to database)
 * - Converts Entity → DTO (before sending response)
 */

package com.erick.spring.converter;

import com.erick.spring.dto.ProductDTO;
import com.erick.spring.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // Lombok generates the constructor automatically
public class ProductConverter {

    // ModelMapper is a library that automatically copies matching fields between objects (DTO ↔ Entity)
    private final ModelMapper modelMapper;


    // Converts a database object (Entity) into a DTO (for API response)
    // ModelMapper matches fields by name (name → name, price → price)
    public ProductDTO entityToDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    // Converts a DTO (incoming request) into an Entity (for database operations)
    // Important: if DTO.id is null → JPA will INSERT
    // if DTO.id has value → JPA will UPDATE
    public ProductEntity dtoToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, ProductEntity.class);
    }

}
