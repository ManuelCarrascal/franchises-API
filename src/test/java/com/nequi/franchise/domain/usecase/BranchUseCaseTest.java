package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.BranchNotFoundException;
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

    @Test
    void updateBranchName_ShouldReturnError_WhenIdIsInvalid() {
        StepVerifier.create(branchUseCase.updateBranchName(0L, "New Name"))
                .expectError(InvalidBranchDataException.class)
                .verify();
    }

    @Test
    void updateBranchName_ShouldReturnError_WhenNameIsInvalid() {
        StepVerifier.create(branchUseCase.updateBranchName(1L, " "))
                .expectError(InvalidBranchDataException.class)
                .verify();
    }

    @Test
    void updateBranchName_ShouldReturnError_WhenBranchNotFound() {
        when(branchPersistencePort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateBranchName(1L, "New Name"))
                .expectError(BranchNotFoundException.class)
                .verify();

        verify(branchPersistencePort).findById(1L);
        verify(branchPersistencePort, never()).saveBranch(any());
    }

    @Test
    void updateBranchName_ShouldUpdateSuccessfully_WhenValid() {
        Branch existingBranch = new Branch(1L, "Old Name", "Address", 2L);
        Branch updatedBranch = new Branch(1L, "New Name", "Address", 2L);

        when(branchPersistencePort.findById(1L)).thenReturn(Mono.just(existingBranch));
        when(branchPersistencePort.saveBranch(any(Branch.class))).thenReturn(Mono.just(updatedBranch));

        StepVerifier.create(branchUseCase.updateBranchName(1L, "New Name"))
                .expectNext(updatedBranch)
                .verifyComplete();

        verify(branchPersistencePort).findById(1L);
        verify(branchPersistencePort).saveBranch(any(Branch.class));
    }
}
