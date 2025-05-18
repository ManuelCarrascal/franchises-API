package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.infrastructure.out.entity.BranchEntity;
import com.nequi.franchise.infrastructure.out.mapper.IBranchEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchPersistenceAdapterTest {

    private BranchRepository branchRepository;
    private IBranchEntityMapper mapper;
    private BranchPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        branchRepository = mock(BranchRepository.class);
        mapper = mock(IBranchEntityMapper.class);
        adapter = new BranchPersistenceAdapter(branchRepository, mapper);
    }

    @Test
    void saveBranch_shouldReturnSavedBranch() {
        Branch model = new Branch(null, "Branch A", "Address A", 1L);
        BranchEntity entity = new BranchEntity();
        BranchEntity savedEntity = new BranchEntity();
        Branch savedModel = new Branch(1L, "Branch A", "Address A", 1L);

        when(mapper.toEntity(model)).thenReturn(entity);
        when(branchRepository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(mapper.toModel(savedEntity)).thenReturn(savedModel);

        StepVerifier.create(adapter.saveBranch(model))
                .expectNext(savedModel)
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnBranch() {
        Long id = 1L;
        BranchEntity entity = new BranchEntity();
        Branch model = new Branch(id, "Branch A", "Address A", 1L);

        when(branchRepository.findById(id)).thenReturn(Mono.just(entity));
        when(mapper.toModel(entity)).thenReturn(model);

        StepVerifier.create(adapter.findById(id))
                .expectNext(model)
                .verifyComplete();
    }
}
