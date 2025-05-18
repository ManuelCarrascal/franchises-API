package com.nequi.franchise.domain.api;

import com.nequi.franchise.domain.model.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {
    Mono<Product> createProduct(Product product);
}
