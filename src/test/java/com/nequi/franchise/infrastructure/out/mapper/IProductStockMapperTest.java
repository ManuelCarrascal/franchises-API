package com.nequi.franchise.infrastructure.out.mapper;

import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.model.ProductByBranchProjection;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IProductStockMapperTest {

    private final IProductStockMapper mapper = Mappers.getMapper(IProductStockMapper.class);

    @Test
    void toModel_shouldMapCorrectly() {
        ProductByBranchProjection projection = new ProductByBranchProjection(1L, "Sucursal 1", "Café", 100);
        ProductByBranch model = mapper.toModel(projection);

        assertEquals(projection.getBranchId(), model.getBranchId());
        assertEquals(projection.getBranchName(), model.getBranchName());
        assertEquals(projection.getProductName(), model.getProductName());
        assertEquals(projection.getStock(), model.getStock());
    }
}
