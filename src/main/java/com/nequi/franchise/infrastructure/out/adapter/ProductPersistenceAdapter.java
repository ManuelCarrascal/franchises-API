package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Product;
import com.nequi.franchise.domain.spi.IProductPersistencePort;
import com.nequi.franchise.infrastructure.out.entity.ProductEntity;
import com.nequi.franchise.infrastructure.out.mapper.IProductEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor

public class ProductPersistenceAdapter implements IProductPersistencePort {
    private final ProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        ProductEntity entity = productEntityMapper.toEntity(product);
        return productRepository.save(entity).map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productRepository.deleteById(id);
    }
}
