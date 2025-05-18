package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.infrastructure.out.entity.FranchiseEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IFranchiseEntityMapperTest {

    private final IFranchiseEntityMapper mapper = Mappers.getMapper(IFranchiseEntityMapper.class);

    @Test
    void toModel_shouldMapCorrectly() {
        FranchiseEntity entity = new FranchiseEntity(1L, "Franquicia X");
        Franchise model = mapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        Franchise model = new Franchise(2L, "Franquicia Y");
        FranchiseEntity entity = mapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
    }
}
