package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.infrastructure.out.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IProductEntityMapperTest {

    private final IProductEntityMapper mapper = Mappers.getMapper(IProductEntityMapper.class);

    @Test
    void toModel_shouldMapCorrectly() {
        ProductEntity entity = new ProductEntity(1L, "Producto", 10.5, 8, 3L);
        Product model = mapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getPrice(), model.getPrice());
        assertEquals(entity.getStock(), model.getStock());
        assertEquals(entity.getBranchId(), model.getBranchId());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        Product model = new Product(2L, "Otra", 5.0, 20, 4L);
        ProductEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getPrice(), entity.getPrice());
        assertEquals(model.getStock(), entity.getStock());
        assertEquals(model.getBranchId(), entity.getBranchId());
    }
}
