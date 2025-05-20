package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.exception.FranchiseNotFoundException;
import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.domain.utils.constants.FranchiseUseCaseConstants.*;

public class FranchiseUseCase implements IFranchiseServicePort {
    private final IFranchisePersistencePort franchisePersistencePort;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        if(franchise.getName() == null || franchise.getName().isEmpty()){
            return Mono.error(new InvalidFranchiseDataException(ERROR_NAME_REQUIRED));
        }

        return franchisePersistencePort.saveFranchise(franchise);
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String newName) {
        if (id == null || id <= MIN_FRANCHISE_ID || newName == null || newName.trim().isEmpty()) {
            return Mono.error(new InvalidFranchiseDataException(ERROR_INVALID_ID_OR_NAME));
        }

        return franchisePersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(ERROR_FRANCHISE_NOT_FOUND)))
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return franchisePersistencePort.saveFranchise(franchise);
                });
    }
}
