package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.infrastructure.out.entity.FranchiseEntity;
import com.nequi.franchise.infrastructure.out.mapper.IFranchiseEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchisePersistenceAdapterTest {

    private FranchiseRepository franchiseRepository;
    private IFranchiseEntityMapper franchiseEntityMapper;
    private FranchisePersistenceAdapter adapter;

    @BeforeEach
    void setup() {
        franchiseRepository = mock(FranchiseRepository.class);
        franchiseEntityMapper = mock(IFranchiseEntityMapper.class);
        adapter = new FranchisePersistenceAdapter(franchiseRepository, franchiseEntityMapper);
    }

    @Test
    void saveFranchise_shouldSaveAndReturnFranchiseModel() {
        // Arrange
        Franchise domainFranchise = new Franchise(null, "Taco Bell");
        FranchiseEntity entityToSave = new FranchiseEntity(null, "Taco Bell");
        FranchiseEntity savedEntity = new FranchiseEntity(1L, "Taco Bell");
        Franchise savedDomainModel = new Franchise(1L, "Taco Bell");

        when(franchiseEntityMapper.toEntity(domainFranchise)).thenReturn(entityToSave);
        when(franchiseRepository.save(entityToSave)).thenReturn(Mono.just(savedEntity));
        when(franchiseEntityMapper.toModel(savedEntity)).thenReturn(savedDomainModel);

        // Act & Assert
        StepVerifier.create(adapter.saveFranchise(domainFranchise))
                .expectNext(savedDomainModel)
                .verifyComplete();

        // Verify interactions
        verify(franchiseEntityMapper).toEntity(domainFranchise);
        verify(franchiseRepository).save(entityToSave);
        verify(franchiseEntityMapper).toModel(savedEntity);
    }
}
