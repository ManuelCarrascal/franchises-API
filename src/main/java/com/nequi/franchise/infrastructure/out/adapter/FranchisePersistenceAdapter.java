package com.nequi.franchise.infrastructure.out.adapter;

import com.nequi.franchise.domain.model.Franchise;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import com.nequi.franchise.infrastructure.out.entity.FranchiseEntity;
import com.nequi.franchise.infrastructure.out.mapper.IFranchiseEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.FranchiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class FranchisePersistenceAdapter implements IFranchisePersistencePort {

    private final FranchiseRepository franchiseRepository;
    private final IFranchiseEntityMapper  franchiseEntityMapper;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        FranchiseEntity entity = franchiseEntityMapper.toEntity(franchise);
        return franchiseRepository.save(entity)
                .map(franchiseEntityMapper::toModel);
    }
}
