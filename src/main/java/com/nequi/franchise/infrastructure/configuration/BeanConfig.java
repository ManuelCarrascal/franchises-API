package com.nequi.franchise.infrastructure.configuration;

import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.application.handler.impl.FranchiseHandler;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import com.nequi.franchise.domain.usecase.FranchiseUseCase;
import org.mapstruct.factory.Mappers;
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

}
