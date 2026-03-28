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
import org.modelmapper.ModelMapper;

public class ProductConverter {

    private ModelMapper modelMapper = new ModelMapper();

    public ProductDTO entityToDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    public ProductEntity dtoToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, ProductEntity.class);
    }

}
