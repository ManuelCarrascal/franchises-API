package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IStockQueryServicePort;
import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.spi.IStockQueryPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import static com.nequi.franchise.domain.utils.constants.StockQueryUseCaseConstants.*;

@RequiredArgsConstructor
public class StockQueryUseCase implements IStockQueryServicePort {

    private final IStockQueryPersistencePort stockQueryPersistencePort;

    @Override
    public Flux<ProductByBranch> getTopProductByStockByFranchise(Long franchiseId) {
        if (franchiseId == null || franchiseId <= MIN_VALID_FRANCHISE_ID) {
            return Flux.error(new InvalidFranchiseDataException(ERROR_INVALID_FRANCHISE_ID));
        }
        return stockQueryPersistencePort.getTopProductByStockByFranchise(franchiseId);
    }
}
