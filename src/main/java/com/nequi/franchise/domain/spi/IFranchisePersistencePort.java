package com.nequi.franchise.domain.spi;

import com.nequi.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistencePort {
    Mono<Franchise> saveFranchise(Franchise franchise);

}
