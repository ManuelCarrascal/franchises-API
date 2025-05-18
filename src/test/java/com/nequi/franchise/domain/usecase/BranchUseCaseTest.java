package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.InvalidBranchDataException;
import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchUseCaseTest {

    private IBranchPersistencePort branchPersistencePort;
    private IFranchisePersistencePort franchisePersistencePort;
    private BranchUseCase branchUseCase;

    @BeforeEach
    void setUp() {
        branchPersistencePort = mock(IBranchPersistencePort.class);
        franchisePersistencePort = mock(IFranchisePersistencePort.class);
        branchUseCase = new BranchUseCase(branchPersistencePort, franchisePersistencePort);
    }

    @Test
    void createBranch_ShouldReturnError_WhenDataIsInvalid() {
        Branch invalidBranch = new Branch(null, null, null, null);

        StepVerifier.create(branchUseCase.createBranch(invalidBranch))
                .expectError(InvalidBranchDataException.class)
                .verify();
    }

    @Test
    void createBranch_ShouldSaveBranch_WhenDataIsValid() {
        Branch branch = new Branch(null, "Branch 1", "Address", 1L);
        Franchise dummyFranchise = new Franchise(1L, "Dummy");

        when(franchisePersistencePort.findById(1L)).thenReturn(Mono.just(dummyFranchise));
        when(branchPersistencePort.saveBranch(branch)).thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.createBranch(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(branchPersistencePort).saveBranch(branch);
    }
}
