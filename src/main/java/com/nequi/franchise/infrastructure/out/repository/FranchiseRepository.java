package com.nequi.franchise.infrastructure.out.repository;

import com.nequi.franchise.infrastructure.out.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity , Long> {
}
