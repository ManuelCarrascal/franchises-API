package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IProductServicePort;
import com.nequi.franchise.domain.exception.BranchNotFoundException;
import com.nequi.franchise.domain.exception.InvalidProductDataException;
import com.nequi.franchise.domain.exception.InvalidStockException;
import com.nequi.franchise.domain.exception.ProductNotFoundException;
import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IProductPersistencePort;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.domain.utils.constants.ProductUseCaseConstants.*;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final IBranchPersistencePort branchPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, IBranchPersistencePort branchPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.branchPersistencePort = branchPersistencePort;
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        if (product.getBranchId() == null || product.getName() == null || product.getPrice() == null) {
            return Mono.error(new InvalidProductDataException(ERROR_REQUIRED_FIELDS));
        }

        return branchPersistencePort.findById(product.getBranchId())
                .switchIfEmpty(Mono.error(new BranchNotFoundException(ERROR_BRANCH_NOT_FOUND)))
                .then(productPersistencePort.saveProduct(product));
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return productPersistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(ERROR_PRODUCT_NOT_FOUND)))
                .then(productPersistencePort.deleteById(productId));
    }

    @Override
    public Mono<Product> updateStock(Long productId, Integer newStock) {
        if (newStock == null || newStock < MIN_STOCK_VALUE) {
            return Mono.error(new InvalidStockException(ERROR_STOCK_VALUE));
        }

        return productPersistencePort.findById(productId)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(ERROR_PRODUCT_NOT_FOUND)))
                .flatMap(product -> {
                    product.setStock(newStock);
                    return productPersistencePort.saveProduct(product);
                });
    }
}
