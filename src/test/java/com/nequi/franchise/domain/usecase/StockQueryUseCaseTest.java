package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.spi.IStockQueryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.nequi.franchise.domain.utils.constants.StockQueryUseCaseConstants.ERROR_INVALID_FRANCHISE_ID;
import static org.mockito.Mockito.*;

class StockQueryUseCaseTest {

    private IStockQueryPersistencePort stockQueryPersistencePort;
    private StockQueryUseCase stockQueryUseCase;

    @BeforeEach
    void setUp() {
        stockQueryPersistencePort = mock(IStockQueryPersistencePort.class);
        stockQueryUseCase = new StockQueryUseCase(stockQueryPersistencePort);
    }

    @Test
    void getTopProductByStockByFranchise_shouldReturnError_whenFranchiseIdIsNull() {
        StepVerifier.create(stockQueryUseCase.getTopProductByStockByFranchise(null))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals(ERROR_INVALID_FRANCHISE_ID)
                )
                .verify();
    }

    @Test
    void getTopProductByStockByFranchise_shouldReturnError_whenFranchiseIdIsInvalid() {
        StepVerifier.create(stockQueryUseCase.getTopProductByStockByFranchise(0L))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals(ERROR_INVALID_FRANCHISE_ID)
                )
                .verify();
    }

    @Test
    void getTopProductByStockByFranchise_shouldReturnResults_whenFranchiseIdIsValid() {
        Long validFranchiseId = 1L;
        ProductByBranch product = new ProductByBranch(1L, "Sucursal 1", "Producto A", 10);

        when(stockQueryPersistencePort.getTopProductByStockByFranchise(validFranchiseId))
                .thenReturn(Flux.just(product));

        StepVerifier.create(stockQueryUseCase.getTopProductByStockByFranchise(validFranchiseId))
                .expectNext(product)
                .verifyComplete();

        verify(stockQueryPersistencePort).getTopProductByStockByFranchise(validFranchiseId);
    }
}
