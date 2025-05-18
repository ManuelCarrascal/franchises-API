package com.nequi.franchise.domain.api;

import com.nequi.franchise.domain.model.ProductByBranch;
import reactor.core.publisher.Flux;

public interface IStockQueryServicePort {
    Flux<ProductByBranch> getTopProductByStockByFranchise(Long franchiseId);
}
