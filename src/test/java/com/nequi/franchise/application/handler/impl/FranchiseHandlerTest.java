package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.FranchiseRequestDto;
import com.nequi.franchise.application.dto.request.FranchiseUpdateRequestDto;
import com.nequi.franchise.application.dto.response.FranchiseResponseDto;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import com.nequi.franchise.domain.model.Franchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class FranchiseHandlerTest {

    private IFranchiseServicePort franchiseServicePort;
    private IFranchiseMapper franchiseMapper;
    private FranchiseHandler handler;

    @BeforeEach
    void setUp() {
        franchiseServicePort = mock(IFranchiseServicePort.class);
        franchiseMapper = mock(IFranchiseMapper.class);
        handler = new FranchiseHandler(franchiseServicePort, franchiseMapper);
    }

    @Test
    void create_shouldReturnBadRequest_whenNameIsEmpty() {
        FranchiseRequestDto dto = new FranchiseRequestDto("   ");
        ServerRequest request = mock(ServerRequest.class);

        when(request.bodyToMono(FranchiseRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.create(request))
                .expectNextMatches(response -> response.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(franchiseServicePort);
    }

    @Test
    void create_shouldReturnOk_whenValidDataProvided() {
        FranchiseRequestDto dto = new FranchiseRequestDto("Taco Bell");
        Franchise model = new Franchise(null, "Taco Bell");
        Franchise saved = new Franchise(1L, "Taco Bell");
        FranchiseResponseDto responseDto = new FranchiseResponseDto(1L, "Taco Bell");

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(FranchiseRequestDto.class)).thenReturn(Mono.just(dto));

        when(franchiseMapper.toModel(dto)).thenReturn(model);
        when(franchiseServicePort.createFranchise(model)).thenReturn(Mono.just(saved));
        when(franchiseMapper.toDto(saved)).thenReturn(responseDto);

        StepVerifier.create(handler.create(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(franchiseMapper).toModel(dto);
        verify(franchiseServicePort).createFranchise(model);
        verify(franchiseMapper).toDto(saved);
    }

    @Test
    void update_shouldReturnBadRequest_whenIdIsInvalid() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("abc");

        StepVerifier.create(handler.update(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(franchiseServicePort);
    }

    @Test
    void update_shouldReturnBadRequest_whenNameIsEmpty() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");

        FranchiseUpdateRequestDto dto = new FranchiseUpdateRequestDto("   ");
        when(request.bodyToMono(FranchiseUpdateRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.update(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(franchiseServicePort);
    }

    @Test
    void update_shouldReturnOk_whenValidDataProvided() {
        Long id = 1L;
        String name = "Updated Franchise";
        FranchiseUpdateRequestDto dto = new FranchiseUpdateRequestDto(name);
        Franchise updated = new Franchise(id, name);
        FranchiseResponseDto responseDto = new FranchiseResponseDto(id, name);

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn(String.valueOf(id));
        when(request.bodyToMono(FranchiseUpdateRequestDto.class)).thenReturn(Mono.just(dto));

        when(franchiseServicePort.updateFranchiseName(id, name)).thenReturn(Mono.just(updated));
        when(franchiseMapper.toDto(updated)).thenReturn(responseDto);

        StepVerifier.create(handler.update(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(franchiseServicePort).updateFranchiseName(id, name);
        verify(franchiseMapper).toDto(updated);
    }
}
