package com.nequi.franchise.domain.usecase;

import com.nequi.franchise.domain.exception.FranchiseNotFoundException;
import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseUseCaseTest {

    private IFranchisePersistencePort persistencePort;
    private FranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IFranchisePersistencePort.class);
        useCase = new FranchiseUseCase(persistencePort);
    }

    @Test
    void createFranchise_shouldReturnSavedFranchise_whenNameIsValid() {
        Franchise input = new Franchise(null, "Taco Bell");
        Franchise saved = new Franchise(1L, "Taco Bell");

        when(persistencePort.saveFranchise(input)).thenReturn(Mono.just(saved));

        StepVerifier.create(useCase.createFranchise(input))
                .expectNext(saved)
                .verifyComplete();

        verify(persistencePort, times(1)).saveFranchise(input);
    }

    @Test
    void createFranchise_shouldReturnError_whenNameIsNull() {
        Franchise input = new Franchise(null, null);

        StepVerifier.create(useCase.createFranchise(input))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals("El nombre de la franquicia es obligatorio"))
                .verify();

        verify(persistencePort, never()).saveFranchise(any());
    }

    @Test
    void createFranchise_shouldReturnError_whenNameIsEmpty() {
        Franchise input = new Franchise(null, "");

        StepVerifier.create(useCase.createFranchise(input))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals("El nombre de la franquicia es obligatorio"))
                .verify();

        verify(persistencePort, never()).saveFranchise(any());
    }

    @Test
    void updateFranchiseName_shouldReturnUpdatedFranchise_whenDataIsValid() {
        Long id = 1L;
        String newName = "New Name";
        Franchise existing = new Franchise(id, "Old Name");
        Franchise updated = new Franchise(id, newName);

        when(persistencePort.findById(id)).thenReturn(Mono.just(existing));
        when(persistencePort.saveFranchise(any())).thenReturn(Mono.just(updated));

        StepVerifier.create(useCase.updateFranchiseName(id, newName))
                .expectNextMatches(franchise -> franchise.getId().equals(id) && franchise.getName().equals(newName))
                .verifyComplete();

        verify(persistencePort).findById(id);
        verify(persistencePort).saveFranchise(existing);
    }

    @Test
    void updateFranchiseName_shouldReturnError_whenIdIsInvalid() {
        StepVerifier.create(useCase.updateFranchiseName(0L, "Valid Name"))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals("Invalid franchise ID or name"))
                .verify();

        verify(persistencePort, never()).findById(any());
        verify(persistencePort, never()).saveFranchise(any());
    }

    @Test
    void updateFranchiseName_shouldReturnError_whenNameIsInvalid() {
        StepVerifier.create(useCase.updateFranchiseName(1L, " "))
                .expectErrorMatches(error ->
                        error instanceof InvalidFranchiseDataException &&
                                error.getMessage().equals("Invalid franchise ID or name"))
                .verify();

        verify(persistencePort, never()).findById(any());
        verify(persistencePort, never()).saveFranchise(any());
    }

    @Test
    void updateFranchiseName_shouldReturnError_whenFranchiseNotFound() {
        Long id = 99L;
        String newName = "New Franchise";

        when(persistencePort.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.updateFranchiseName(id, newName))
                .expectErrorMatches(error ->
                        error instanceof FranchiseNotFoundException &&
                                error.getMessage().equals("Franchise not found"))
                .verify();

        verify(persistencePort).findById(id);
        verify(persistencePort, never()).saveFranchise(any());
    }
}
