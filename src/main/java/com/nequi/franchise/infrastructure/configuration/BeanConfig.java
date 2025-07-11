package com.nequi.franchise.infrastructure.configuration;

import com.nequi.franchise.application.handler.IBranchHandler;
import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.application.handler.IProductHandler;
import com.nequi.franchise.application.handler.IStockQueryHandler;
import com.nequi.franchise.application.handler.impl.BranchHandler;
import com.nequi.franchise.application.handler.impl.FranchiseHandler;
import com.nequi.franchise.application.handler.impl.ProductHandler;
import com.nequi.franchise.application.handler.impl.StockQueryHandler;
import com.nequi.franchise.application.mapper.IBranchMapper;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.application.mapper.IProductMapper;
import com.nequi.franchise.domain.api.IBranchServicePort;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.api.IProductServicePort;
import com.nequi.franchise.domain.api.IStockQueryServicePort;
import com.nequi.franchise.domain.spi.IBranchPersistencePort;
import com.nequi.franchise.domain.spi.IFranchisePersistencePort;
import com.nequi.franchise.domain.spi.IProductPersistencePort;
import com.nequi.franchise.domain.spi.IStockQueryPersistencePort;
import com.nequi.franchise.domain.usecase.BranchUseCase;
import com.nequi.franchise.domain.usecase.FranchiseUseCase;
import com.nequi.franchise.domain.usecase.ProductUseCase;
import com.nequi.franchise.domain.usecase.StockQueryUseCase;
import com.nequi.franchise.infrastructure.out.adapter.BranchPersistenceAdapter;
import com.nequi.franchise.infrastructure.out.adapter.StockQueryPersistenceAdapter;
import com.nequi.franchise.infrastructure.out.mapper.IBranchEntityMapper;
import com.nequi.franchise.infrastructure.out.mapper.IProductStockMapper;
import com.nequi.franchise.infrastructure.out.repository.BranchRepository;
import com.nequi.franchise.infrastructure.out.repository.ProductCustomRepository;
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
    public IFranchiseHandler franchiseHandler(IFranchiseServicePort franchiseServicePort, IFranchiseMapper franchiseMapper) {
        return new FranchiseHandler(franchiseServicePort, franchiseMapper);
    }

    @Bean
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

    @Bean
    public IProductServicePort productServicePort(IProductPersistencePort productPersistencePort, IBranchPersistencePort branchPersistencePort) {
        return new ProductUseCase(productPersistencePort, branchPersistencePort);
    }

    @Bean
    public IProductHandler productHandler(IProductServicePort servicePort,  IProductMapper mapper) {
        return new ProductHandler(servicePort, mapper);
    }

    @Bean
    public IStockQueryServicePort stockQueryServicePort(IStockQueryPersistencePort port) {
        return new StockQueryUseCase(port);
    }

    @Bean
    public IStockQueryHandler stockQueryHandler(IStockQueryServicePort servicePort) {
        return new StockQueryHandler(servicePort);
    }

    @Bean
    public IStockQueryPersistencePort stockQueryPersistencePort(ProductCustomRepository repository, IProductStockMapper mapper) {
        return new StockQueryPersistenceAdapter(repository, mapper);
    }
}
