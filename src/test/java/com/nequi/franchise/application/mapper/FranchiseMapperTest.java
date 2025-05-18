package com.nequi.franchise.application.mapper;

import com.nequi.franchise.application.dto.request.FranchiseRequestDto;
import com.nequi.franchise.application.dto.response.FranchiseResponseDto;
import com.nequi.franchise.domain.model.Franchise;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseMapperTest {

    private final IFranchiseMapper mapper = Mappers.getMapper(IFranchiseMapper.class);

    @Test
    void toModel_shouldMapDtoToModel() {
        FranchiseRequestDto dto = new FranchiseRequestDto("KFC");

        Franchise model = mapper.toModel(dto);

        assertNotNull(model);
        assertEquals("KFC", model.getName());
        assertNull(model.getId());
    }

    @Test
    void toDto_shouldMapModelToDto() {
        Franchise model = new Franchise(1L, "Burger King");

        FranchiseResponseDto dto = mapper.toDto(model);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Burger King", dto.getName());
    }
}
