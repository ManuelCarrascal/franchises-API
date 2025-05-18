package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.BranchNotFoundException;
import com.nequi.franchise.domain.exception.InvalidProductDataException;
import com.nequi.franchise.domain.exception.ProductNotFoundException;
import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    private IProductPersistencePort productPersistencePort;
    private IBranchPersistencePort branchPersistencePort;
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        productPersistencePort = mock(IProductPersistencePort.class);
        branchPersistencePort = mock(IBranchPersistencePort.class);
        productUseCase = new ProductUseCase(productPersistencePort, branchPersistencePort);
    }

    @Test
    void createProduct_shouldReturnError_whenDataIsInvalid() {
        Product invalidProduct = new Product(null, null, null, null);

        StepVerifier.create(productUseCase.createProduct(invalidProduct))
                .expectError(InvalidProductDataException.class)
                .verify();
    }


    @Test
    void createProduct_shouldSaveProduct_whenDataIsValid() {
        Product product = new Product(null, "Product 1", 10.0, 1L);
        Branch dummyBranch = new Branch(1L, "Branch 1", "Address", 2L);

        when(branchPersistencePort.findById(1L)).thenReturn(Mono.just(dummyBranch));
        when(productPersistencePort.saveProduct(product)).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.createProduct(product))
                .expectNext(product)
                .verifyComplete();

        verify(productPersistencePort).saveProduct(product);
    }

    @Test
    void deleteProduct_shouldDeleteProduct_whenProductExists() {
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Product 1", 10.0, 1L);

        when(productPersistencePort.findById(productId)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.deleteById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct(productId))
                .verifyComplete();

        verify(productPersistencePort).findById(productId);
        verify(productPersistencePort).deleteById(productId);
    }
}
