package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.dto.request.BranchUpdateRequestDto;
import com.nequi.franchise.application.dto.response.BranchResponseDto;
import com.nequi.franchise.application.mapper.IBranchMapper;
import com.nequi.franchise.domain.api.IBranchServicePort;
import com.nequi.franchise.domain.model.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchHandlerTest {

    private IBranchServicePort branchServicePort;
    private IBranchMapper branchMapper;
    private BranchHandler handler;

    @BeforeEach
    void setUp() {
        branchServicePort = mock(IBranchServicePort.class);
        branchMapper = mock(IBranchMapper.class);
        handler = new BranchHandler(branchServicePort, branchMapper);
    }


    @Test
    void create_shouldReturnBadRequest_whenRequiredFieldsMissing() {
        BranchRequestDto invalidDto = new BranchRequestDto(null, "", "");

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(BranchRequestDto.class)).thenReturn(Mono.just(invalidDto));

        StepVerifier.create(handler.create(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(branchServicePort);
    }

    @Test
    void create_shouldReturnOk_whenValidDataProvided() {
        BranchRequestDto dto = new BranchRequestDto(1L, "Sucursal 1", "Calle 123");
        Branch model = new Branch(null, "Sucursal 1", "Calle 123", 1L);
        Branch saved = new Branch(1L, "Sucursal 1", "Calle 123", 1L);
        BranchResponseDto responseDto = new BranchResponseDto(1L, 1L, "Calle 123", "1L");

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(BranchRequestDto.class)).thenReturn(Mono.just(dto));
        when(branchMapper.toModel(dto)).thenReturn(model);
        when(branchServicePort.createBranch(model)).thenReturn(Mono.just(saved));
        when(branchMapper.toDto(saved)).thenReturn(responseDto);

        StepVerifier.create(handler.create(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(branchServicePort).createBranch(model);
        verify(branchMapper).toDto(saved);
    }


    @Test
    void update_Branch_shouldReturnBadRequest_whenIdIsInvalid() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("abc");

        StepVerifier.create(handler.updateBranch(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(branchServicePort);
    }

    @Test
    void update_Branch_shouldReturnBadRequest_whenNameIsEmpty() {
        BranchUpdateRequestDto dto = new BranchUpdateRequestDto("   ");

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(BranchUpdateRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.updateBranch(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(branchServicePort);
    }

    @Test
    void update_shouldReturnOk_whenValidUpdateBranch() {
        Long id = 1L;
        BranchUpdateRequestDto dto = new BranchUpdateRequestDto("Sucursal Editada");
        Branch updated = new Branch(id, "Sucursal Editada", "Dir", 99L);
        BranchResponseDto responseDto = new BranchResponseDto(id, 1L, "Dir", "99L");

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(BranchUpdateRequestDto.class)).thenReturn(Mono.just(dto));
        when(branchServicePort.updateBranchName(id, dto.getName())).thenReturn(Mono.just(updated));
        when(branchMapper.toDto(updated)).thenReturn(responseDto);

        StepVerifier.create(handler.updateBranch(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(branchServicePort).updateBranchName(id, dto.getName());
        verify(branchMapper).toDto(updated);
    }
}
