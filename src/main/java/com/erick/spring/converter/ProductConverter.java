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
