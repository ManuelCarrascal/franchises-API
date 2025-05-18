package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IStockQueryServicePort;
import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.spi.IStockQueryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class StockQueryUseCase implements IStockQueryServicePort {

    private final IStockQueryPersistencePort stockQueryPersistencePort;

    @Override
    public Flux<ProductByBranch> getTopProductByStockByFranchise(Long franchiseId) {
        return stockQueryPersistencePort.getTopProductByStockByFranchise(franchiseId);
    }
}
