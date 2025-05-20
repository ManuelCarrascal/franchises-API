package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.domain.api.IStockQueryServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.Mockito.*;

class StockQueryHandlerTest {

    private IStockQueryServicePort servicePort;
    private StockQueryHandler handler;

    @BeforeEach
    void setUp() {
        servicePort = mock(IStockQueryServicePort.class);
        handler = new StockQueryHandler(servicePort);
    }

    @Test
    void getTopStockProducts_shouldReturnBadRequest_forInvalidId() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("abc");

        StepVerifier.create(handler.getTopStockProducts(request))
                .expectNextMatches(response -> response.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void getTopStockProducts_shouldReturnOk_whenValidId() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");

        when(servicePort.getTopProductByStockByFranchise(1L))
                .thenReturn(Flux.fromIterable(Collections.emptyList()));

        StepVerifier.create(handler.getTopStockProducts(request))
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
}
