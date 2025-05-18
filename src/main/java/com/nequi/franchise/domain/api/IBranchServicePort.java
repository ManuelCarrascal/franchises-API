package com.nequi.franchise.domain.api;

import com.nequi.franchise.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {
    Mono<Branch> createBranch(Branch branch);
}
