package com.nequi.franchise.application.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IStockQueryHandler {
    Mono<ServerResponse> getTopStockProducts(ServerRequest request);
}
