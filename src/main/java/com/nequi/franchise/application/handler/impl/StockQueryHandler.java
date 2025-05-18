package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.handler.IStockQueryHandler;
import com.nequi.franchise.domain.api.IStockQueryServicePort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class StockQueryHandler implements IStockQueryHandler {

    private final IStockQueryServicePort stockQueryService;

    public StockQueryHandler(IStockQueryServicePort stockQueryService) {
        this.stockQueryService = stockQueryService;
    }

    @Override
    public Mono<ServerResponse> getTopStockProducts(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockQueryService.getTopProductByStockByFranchise(franchiseId), Object.class);
    }
}
