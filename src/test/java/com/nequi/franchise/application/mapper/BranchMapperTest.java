package com.nequi.franchise.application.mapper;

import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.dto.response.BranchResponseDto;
import com.nequi.franchise.domain.model.Branch;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BranchMapperTest {

    private final IBranchMapper mapper = Mappers.getMapper(IBranchMapper.class);

    @Test
    void toModel_shouldMapDtoToModel() {
        BranchRequestDto dto = new BranchRequestDto(2L, "Branch Centro", "Cra 12 #34-56");

        Branch model = mapper.toModel(dto);

        assertNotNull(model);
        assertNull(model.getId());
        assertEquals("Branch Centro", model.getName());
        assertEquals("Cra 12 #34-56", model.getAddress());
        assertEquals(2L, model.getFranchiseId());
    }

    @Test
    void toDto_shouldMapModelToDto() {
        Branch model = new Branch(5L, "Sucursal Norte", "Calle 123", 10L);

        BranchResponseDto dto = mapper.toDto(model);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("Sucursal Norte", dto.getName());
        assertEquals("Calle 123", dto.getAddress());
        assertEquals(10L, dto.getFranchiseId());
    }
}
