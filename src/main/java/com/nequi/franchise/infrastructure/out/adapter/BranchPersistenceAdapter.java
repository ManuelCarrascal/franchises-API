package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Branch;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.infrastructure.out.entity.BranchEntity;
import com.nequi.franchise.infrastructure.out.mapper.IBranchEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BranchPersistenceAdapter implements IBranchPersistencePort {

    private final BranchRepository branchRepository;
    private final IBranchEntityMapper branchEntityMapper;

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        BranchEntity entity = branchEntityMapper.toEntity(branch);
        return branchRepository.save(entity)
                .map(branchEntityMapper::toModel);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return branchRepository.findById(id)
                .map(branchEntityMapper::toModel);
    }
}
