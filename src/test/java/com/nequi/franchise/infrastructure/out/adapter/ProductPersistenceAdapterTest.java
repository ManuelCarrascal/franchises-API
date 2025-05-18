package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.infrastructure.out.entity.ProductEntity;
import com.nequi.franchise.infrastructure.out.mapper.IProductEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductPersistenceAdapterTest {

    private ProductRepository productRepository;
    private IProductEntityMapper mapper;
    private ProductPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        mapper = mock(IProductEntityMapper.class);
        adapter = new ProductPersistenceAdapter(productRepository, mapper);
    }

    @Test
    void saveProduct_shouldReturnSavedProduct() {
        Product model = new Product(null, "Burger", 10.0, 5, 1L);
        ProductEntity entity = new ProductEntity();
        ProductEntity savedEntity = new ProductEntity();
        Product savedModel = new Product(1L, "Burger", 10.0, 5, 1L);

        when(mapper.toEntity(model)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toModel(savedEntity)).thenReturn(savedModel);

        StepVerifier.create(adapter.saveProduct(model))
                .expectNext(savedModel)
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnProduct() {
        Long id = 1L;
        ProductEntity entity = new ProductEntity();
        Product model = new Product(id, "Burger", 10.0, 5, 1L);

        when(productRepository.findById(id)).thenReturn(Mono.just(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        StepVerifier.create(adapter.findById(id))
                .expectNext(model)
                .verifyComplete();
    }

    @Test
    void deleteById_shouldCompleteWithoutError() {
        Long id = 1L;

        when(productRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(id))
                .verifyComplete();
    }
}
