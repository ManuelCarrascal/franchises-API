package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.infrastructure.out.entity.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBranchEntityMapper {
    Branch toModel(BranchEntity entity);
    BranchEntity toEntity(Branch model);
}
