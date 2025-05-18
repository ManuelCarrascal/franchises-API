package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IProductServicePort;
import com.nequi.franchise.domain.exception.InvalidProductDataException;
import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.domain.spi.IProductPersistencePort;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort persistencePort;

    public ProductUseCase(IProductPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }


    @Override
    public Mono<Product> createProduct(Product product) {
        if (product.getBranchId() == null || product.getName() == null || product.getPrice() == null) {
            return Mono.error(new InvalidProductDataException("All product fields are required"));
        }
        return persistencePort.saveProduct(product);
    }
}
