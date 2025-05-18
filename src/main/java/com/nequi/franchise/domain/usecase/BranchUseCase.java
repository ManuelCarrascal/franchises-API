package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IBranchServicePort;
import com.nequi.franchise.domain.exception.FranchiseNotFoundException;
import com.nequi.franchise.domain.exception.InvalidBranchDataException;
import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final IFranchisePersistencePort franchisePersistencePort;

    public BranchUseCase(IBranchPersistencePort branchPersistencePort, IFranchisePersistencePort franchisePersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        if (branch.getFranchiseId() == null || branch.getName() == null || branch.getAddress() == null) {
            return Mono.error(new InvalidBranchDataException("All fields are required"));
        }

        return franchisePersistencePort.findById(branch.getFranchiseId())
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException("The franchise does not exist")))
                .then(branchPersistencePort.saveBranch(branch));
    }
}
