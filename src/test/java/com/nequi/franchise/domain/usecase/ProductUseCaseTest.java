package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.InvalidProductDataException;
import com.nequi.franchise.domain.exception.InvalidStockException;
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
        Product invalidProduct = new Product(null, null, null,null, null);

        StepVerifier.create(productUseCase.createProduct(invalidProduct))
                .expectError(InvalidProductDataException.class)
                .verify();
    }


    @Test
    void createProduct_shouldSaveProduct_whenDataIsValid() {
        Product product = new Product(null, "Product 1", 10.0,4, 1L);
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
        Product existingProduct = new Product(productId, "Product 1", 10.0,4 ,1L);

        when(productPersistencePort.findById(productId)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.deleteById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct(productId))
                .verifyComplete();

        verify(productPersistencePort).findById(productId);
        verify(productPersistencePort).deleteById(productId);
    }

    @Test
    void updateStock_shouldReturnError_whenStockIsInvalid() {
        Long productId = 1L;

        // Stock nulo
        StepVerifier.create(productUseCase.updateStock(productId, null))
                .expectError(InvalidStockException.class)
                .verify();

        // Stock negativo
        StepVerifier.create(productUseCase.updateStock(productId, -1))
                .expectError(InvalidStockException.class)
                .verify();
    }

    @Test
    void updateStock_shouldReturnError_whenProductNotFound() {
        Long productId = 1L;
        Integer newStock = 5;

        when(productPersistencePort.findById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateStock(productId, newStock))
                .expectError(ProductNotFoundException.class)
                .verify();
    }

    @Test
    void updateStock_shouldUpdateStock_whenProductExists() {
        Long productId = 1L;
        Integer newStock = 20;
        Product product = new Product(productId, "Product 1", 10.0, 4, 1L);

        when(productPersistencePort.findById(productId)).thenReturn(Mono.just(product));
        when(productPersistencePort.saveProduct(any(Product.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productUseCase.updateStock(productId, newStock))
                .assertNext(updatedProduct -> {
                    assert updatedProduct.getStock().equals(newStock);
                })
                .verifyComplete();

        verify(productPersistencePort).findById(productId);
        verify(productPersistencePort).saveProduct(any(Product.class));
    }

    @Test
    void updateName_shouldReturnError_whenProductIdOrNameIsInvalid() {
        StepVerifier.create(productUseCase.updateName(null, "New Name"))
                .expectError(InvalidProductDataException.class)
                .verify();

        StepVerifier.create(productUseCase.updateName(0L, "New Name"))
                .expectError(InvalidProductDataException.class)
                .verify();

        StepVerifier.create(productUseCase.updateName(1L, null))
                .expectError(InvalidProductDataException.class)
                .verify();

        StepVerifier.create(productUseCase.updateName(1L, " "))
                .expectError(InvalidProductDataException.class)
                .verify();
    }

    @Test
    void updateName_shouldReturnError_whenProductNotFound() {
        Long productId = 1L;
        String newName = "Updated Name";

        when(productPersistencePort.findById(productId)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateName(productId, newName))
                .expectError(ProductNotFoundException.class)
                .verify();
    }

    @Test
    void updateName_shouldUpdateName_whenProductExists() {
        Long productId = 1L;
        String newName = "Updated Name";
        Product existingProduct = new Product(productId, "Old Name", 10.0, 5, 1L);

        when(productPersistencePort.findById(productId)).thenReturn(Mono.just(existingProduct));
        when(productPersistencePort.saveProduct(any(Product.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productUseCase.updateName(productId, newName))
                .assertNext(updatedProduct -> {
                    assert updatedProduct.getName().equals(newName);
                })
                .verifyComplete();

        verify(productPersistencePort).findById(productId);
        verify(productPersistencePort).saveProduct(any(Product.class));
    }
}
