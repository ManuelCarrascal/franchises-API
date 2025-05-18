package com.nequi.franchise.domain.api;

import com.nequi.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> updateFranchiseName(Long id, String newName);

}
