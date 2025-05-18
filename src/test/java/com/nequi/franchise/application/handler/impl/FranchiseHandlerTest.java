package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.response.FranchiseResponseDto;
import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.model.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

class FranchiseHandlerTest {

    private IFranchiseServicePort servicePort;
    private IFranchiseMapper mapper;
    private WebTestClient client;

    @BeforeEach
    void setup() {
        servicePort = mock(IFranchiseServicePort.class);
        mapper = mock(IFranchiseMapper.class);
        IFranchiseHandler handler = new FranchiseHandler(servicePort, mapper);

        RouterFunction<ServerResponse> route = route(POST("/api/franchises"), handler::create);
        client = WebTestClient.bindToRouterFunction(route).build();
    }

    @Test
    void createFranchise_shouldReturn200_whenValidRequest() {
        Franchise model = new Franchise(null, "Taco Bell");
        Franchise savedModel = new Franchise(1L, "Taco Bell");
        FranchiseResponseDto responseDto = new FranchiseResponseDto(1L, "Taco Bell");

        when(mapper.toModel(any())).thenReturn(model);
        when(servicePort.createFranchise(model)).thenReturn(Mono.just(savedModel));
        when(mapper.toDto(savedModel)).thenReturn(responseDto);

        client.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Taco Bell\"}")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Taco Bell");

        verify(servicePort, times(1)).createFranchise(model);
    }

    @Test
    void createFranchise_shouldReturn400_whenNameIsNull() {
        client.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":null}")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class).isEqualTo("Franchise name must not be empty");

        verify(servicePort, never()).createFranchise(any());
    }

    @Test
    void createFranchise_shouldReturn400_whenNameIsBlank() {
        client.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"   \"}")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class).isEqualTo("Franchise name must not be empty");

        verify(servicePort, never()).createFranchise(any());
    }
}
