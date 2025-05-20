package com.nequi.franchise.infrastructure.out.repository;

import com.nequi.franchise.infrastructure.out.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long>, ProductCustomRepository {
}