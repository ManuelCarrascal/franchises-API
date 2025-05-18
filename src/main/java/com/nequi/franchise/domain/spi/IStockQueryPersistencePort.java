package com.nequi.franchise.domain.spi;

import com.nequi.franchise.domain.model.ProductByBranch;
import reactor.core.publisher.Flux;

public interface IStockQueryPersistencePort {
    Flux<ProductByBranch> getTopProductByStockByFranchise(Long franchiseId);
}
