package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.handler.IBranchHandler;
import com.nequi.franchise.application.mapper.IBranchMapper;
import com.nequi.franchise.domain.api.IBranchServicePort;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@AllArgsConstructor
public class BranchHandler implements IBranchHandler {

    private IBranchServicePort branchServicePort;
    private IBranchMapper branchMapper;

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .flatMap(dto -> branchServicePort.createBranch(branchMapper.toModel(dto)))
                .map(branchMapper::toDto)
                .flatMap(res -> ServerResponse.ok().bodyValue(res));
    }
}
