package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.api.IBranchServicePort;
import com.nequi.franchise.domain.exception.BranchNotFoundException;
import com.nequi.franchise.domain.exception.FranchiseNotFoundException;
import com.nequi.franchise.domain.exception.InvalidBranchDataException;
import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.domain.utils.constants.BranchUseCaseConstants.*;

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
            return Mono.error(new InvalidBranchDataException(ERROR_REQUIRED_FIELDS));
        }

        return franchisePersistencePort.findById(branch.getFranchiseId())
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(ERROR_FRANCHISE_NOT_FOUND)))
                .then(branchPersistencePort.saveBranch(branch));
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String newName) {
        if (id == null || id <= MIN_VALID_BRANCH_ID || newName == null || newName.trim().isEmpty()) {
            return Mono.error(new InvalidBranchDataException(ERROR_INVALID_BRANCH_ID_OR_NAME));
        }

        return branchPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new BranchNotFoundException(ERROR_BRANCH_NOT_FOUND)))
                .flatMap(branch -> {
                    branch.setName(newName);
                    return branchPersistencePort.saveBranch(branch);
                });
    }
}
