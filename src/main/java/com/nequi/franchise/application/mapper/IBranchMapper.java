package com.nequi.franchise.application.mapper;


import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.dto.response.BranchResponseDto;
import com.nequi.franchise.domain.model.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBranchMapper {
    @Mapping( target = "id", ignore = true)
    Branch toModel(BranchRequestDto dto);
    BranchResponseDto toDto(Branch model);
}