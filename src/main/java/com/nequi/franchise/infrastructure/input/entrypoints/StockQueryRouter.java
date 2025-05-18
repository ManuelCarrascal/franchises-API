package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.handler.IStockQueryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class StockQueryRouter {

    @Bean
    public RouterFunction<ServerResponse> stockRoutes(IStockQueryHandler handler) {
        return RouterFunctions.route(GET("/api/franchises/{id}/top-stock-products"), handler::getTopStockProducts);
    }
}
