package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.model.ProductByBranchProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProductStockMapper {
    ProductByBranch toModel(ProductByBranchProjection projection);
}