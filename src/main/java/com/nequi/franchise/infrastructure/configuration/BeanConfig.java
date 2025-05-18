package com.nequi.franchise.infrastructure.configuration;

import com.nequi.franchise.application.handler.IBranchHandler;
import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.application.handler.impl.BranchHandler;
import com.nequi.franchise.application.handler.impl.FranchiseHandler;
import com.nequi.franchise.application.mapper.IBranchMapper;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.domain.api.IBranchServicePort;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import com.nequi.franchise.domain.usecase.BranchUseCase;
import com.nequi.franchise.domain.usecase.FranchiseUseCase;
import com.nequi.franchise.infrastructure.out.adapter.BranchPersistenceAdapter;
import com.nequi.franchise.infrastructure.out.mapper.IBranchEntityMapper;
import com.nequi.franchise.infrastructure.out.repository.BranchRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public IFranchiseServicePort franchiseServicePort(IFranchisePersistencePort franchisePersistencePort) {
        return new FranchiseUseCase(franchisePersistencePort);
    }

    @Bean
    public IFranchiseMapper franchiseMapper() {
        return Mappers.getMapper(IFranchiseMapper.class);
    }

    @Bean
    public IFranchiseHandler franchiseHandler (IFranchiseServicePort franchiseServicePort, IFranchiseMapper franchiseMapper){
        return new FranchiseHandler(franchiseServicePort,franchiseMapper);
    }

    @Bean(name = "branchPersistencePort")
    public IBranchPersistencePort branchPersistencePort(BranchRepository repository, IBranchEntityMapper mapper) {
        return new BranchPersistenceAdapter(repository, mapper);
    }

    @Bean
    public IBranchServicePort branchServicePort(
            @Qualifier("branchPersistencePort") IBranchPersistencePort persistencePort,
            IFranchisePersistencePort franchisePersistencePort) {
        return new BranchUseCase(persistencePort, franchisePersistencePort);
    }

    @Bean
    public IBranchHandler branchHandler(IBranchServicePort servicePort, IBranchMapper mapper) {
        return new BranchHandler(servicePort, mapper);
    }

}
