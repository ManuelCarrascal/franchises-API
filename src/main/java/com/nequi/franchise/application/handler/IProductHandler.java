package com.nequi.franchise.application.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IProductHandler{
    Mono<ServerResponse> create(ServerRequest request);
    Mono<ServerResponse> delete(ServerRequest request);
    Mono<ServerResponse> updateStock(ServerRequest request);

}
