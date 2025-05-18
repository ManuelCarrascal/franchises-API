package com.nequi.franchise.application.mapper;

import com.nequi.franchise.application.dto.request.FranchiseRequestDto;
import com.nequi.franchise.application.dto.response.FranchiseResponseDto;
import com.nequi.franchise.domain.model.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IFranchiseMapper {
    @Mapping(target = "id", ignore = true)
    Franchise toModel(FranchiseRequestDto dto);
    FranchiseResponseDto toDto(Franchise model);
}
