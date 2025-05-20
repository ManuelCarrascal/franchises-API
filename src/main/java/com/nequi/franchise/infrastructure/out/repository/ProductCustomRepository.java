package com.nequi.franchise.infrastructure.out.repository;

import com.nequi.franchise.domain.model.ProductByBranchProjection;
import reactor.core.publisher.Flux;

public interface ProductCustomRepository {
    Flux<ProductByBranchProjection> findTopStockProductByFranchise(Long franchiseId);

}
