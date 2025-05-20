package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.spi.IStockQueryPersistencePort;
import com.nequi.franchise.infrastructure.out.mapper.IProductStockMapper;
import com.nequi.franchise.infrastructure.out.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class StockQueryPersistenceAdapter implements IStockQueryPersistencePort {

    private final ProductCustomRepository productCustomRepository;
    private final IProductStockMapper productStockMapper;

    @Override
    public Flux<ProductByBranch> getTopProductByStockByFranchise(Long franchiseId) {
        return productCustomRepository.findTopStockProductByFranchise(franchiseId)
                .map(productStockMapper::toModel);
    }
}