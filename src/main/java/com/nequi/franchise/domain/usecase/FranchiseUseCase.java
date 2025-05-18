package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {
    private final IFranchisePersistencePort franchisePersistencePort;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        if(franchise.getName() == null || franchise.getName().isEmpty()){
            return Mono.error(new InvalidFranchiseDataException("El nombre de la franquicia es obligatorio"));
        }

        return franchisePersistencePort.saveFranchise(franchise);
    }
}
