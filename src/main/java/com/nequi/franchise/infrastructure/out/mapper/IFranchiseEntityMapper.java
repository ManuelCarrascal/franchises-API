package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.infrastructure.out.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFranchiseEntityMapper {
    Franchise toModel(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise model);
}
