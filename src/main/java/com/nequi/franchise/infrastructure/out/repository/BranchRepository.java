package com.nequi.franchise.infrastructure.out.repository;

import com.nequi.franchise.infrastructure.out.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchRepository  extends ReactiveCrudRepository<BranchEntity, Long> {

}
