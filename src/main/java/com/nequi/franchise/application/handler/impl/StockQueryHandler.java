package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.handler.IStockQueryHandler;
import com.nequi.franchise.domain.api.IStockQueryServicePort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.application.util.constants.StockQueryHandlerConstants.*;

public class StockQueryHandler implements IStockQueryHandler {

    private final IStockQueryServicePort stockQueryService;

    public StockQueryHandler(IStockQueryServicePort stockQueryService) {
        this.stockQueryService = stockQueryService;
    }

    @Override
    public Mono<ServerResponse> getTopStockProducts(ServerRequest request) {
        try {
            Long franchiseId = Long.parseLong(request.pathVariable(PATH_VARIABLE_ID));
            if (franchiseId <= MIN_VALID_FRANCHISE_ID) {
                return ServerResponse.badRequest().bodyValue(ERROR_NON_POSITIVE_FRANCHISE_ID);
            }

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(stockQueryService.getTopProductByStockByFranchise(franchiseId), Object.class);

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue(ERROR_INVALID_FRANCHISE_ID);
        }
    }
}
