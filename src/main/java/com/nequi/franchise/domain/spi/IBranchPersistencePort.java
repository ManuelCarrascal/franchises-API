package com.nequi.franchise.domain.spi;

import com.nequi.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {
    Mono<Branch> saveBranch(Branch branch);
}
