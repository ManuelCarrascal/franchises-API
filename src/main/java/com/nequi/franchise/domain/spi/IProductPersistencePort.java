package com.nequi.franchise.domain.spi;

import com.nequi.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> saveProduct(Product product);
    Mono<Product> findById(Long id);
    Mono<Void> deleteById(Long id);

}
