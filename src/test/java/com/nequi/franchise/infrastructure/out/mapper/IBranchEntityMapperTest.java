package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.infrastructure.out.entity.BranchEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IBranchEntityMapperTest {

    private final IBranchEntityMapper mapper = Mappers.getMapper(IBranchEntityMapper.class);

    @Test
    void toModel_shouldMapCorrectly() {
        BranchEntity entity = new BranchEntity(1L, "Sucursal", "Calle 123", 5L);
        Branch model = mapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getAddress(), model.getAddress());
        assertEquals(entity.getFranchiseId(), model.getFranchiseId());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        Branch model = new Branch(2L, "Otra", "Cra 456", 10L);
        BranchEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getAddress(), entity.getAddress());
        assertEquals(model.getFranchiseId(), entity.getFranchiseId());
    }
}
