package com.nequi.franchise.application.mapper;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.dto.response.ProductResponseDto;
import com.nequi.franchise.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    @Mapping( target = "id", ignore = true)
    Product toModel(ProductRequestDto dto);
    ProductResponseDto toDto(Product product);
}