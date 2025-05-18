package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.ProductByBranch;
import com.nequi.franchise.domain.model.ProductByBranchProjection;
import com.nequi.franchise.infrastructure.out.mapper.IProductStockMapper;
import com.nequi.franchise.infrastructure.out.repository.ProductCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class StockQueryPersistenceAdapterTest {

    private ProductCustomRepository repository;
    private IProductStockMapper mapper;
    private StockQueryPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(ProductCustomRepository.class);
        mapper = mock(IProductStockMapper.class);
        adapter = new StockQueryPersistenceAdapter(repository, mapper);
    }

    @Test
    void getTopProductByStockByFranchise_shouldReturnMappedResults() {
        Long franchiseId = 1L;
        ProductByBranchProjection projection = new ProductByBranchProjection(1L, "Branch 1", "Burger", 10);
        ProductByBranch dto = new ProductByBranch(1L, "Branch 1", "Burger", 10);

        when(repository.findTopStockProductByFranchise(franchiseId)).thenReturn(Flux.just(projection));
        when(mapper.toModel(projection)).thenReturn(dto);

        StepVerifier.create(adapter.getTopProductByStockByFranchise(franchiseId))
                .expectNext(dto)
                .verifyComplete();
    }
}
