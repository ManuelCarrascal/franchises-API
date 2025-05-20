package com.nequi.franchise.application.mapper;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.dto.response.ProductResponseDto;
import com.nequi.franchise.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final IProductMapper mapper = Mappers.getMapper(IProductMapper.class);

    @Test
    void toModel_shouldMapDtoToModel() {
        ProductRequestDto dto = new ProductRequestDto("Empanada", 2500.0, 30, 3L);

        Product model = mapper.toModel(dto);

        assertNotNull(model);
        assertNull(model.getId()); // porque lo ignora el mapper
        assertEquals("Empanada", model.getName());
        assertEquals(2500.0, model.getPrice());
        assertEquals(30, model.getStock());
        assertEquals(3L, model.getBranchId());
    }

    @Test
    void toDto_shouldMapModelToDto() {
        Product product = new Product(10L, "Jugo", 4000.0, 50, 5L);

        ProductResponseDto dto = mapper.toDto(product);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Jugo", dto.getName());
        assertEquals(4000.0, dto.getPrice());
        assertEquals(50, dto.getStock());
        assertEquals(5L, dto.getBranchId());
    }
}
