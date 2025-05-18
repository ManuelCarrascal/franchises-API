package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.infrastructure.out.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {

    Product toModel(ProductEntity entity);
    ProductEntity toEntity(Product model);
}