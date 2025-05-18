package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.FranchiseRequestDto;
import com.nequi.franchise.application.dto.request.FranchiseUpdateRequestDto;
import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.application.mapper.IFranchiseMapper;
import com.nequi.franchise.domain.api.IFranchiseServicePort;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@AllArgsConstructor
public class FranchiseHandler implements IFranchiseHandler {

    private final IFranchiseServicePort franchiseServicePort;
    private final IFranchiseMapper franchiseMapper;

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .flatMap(dto -> {
                    if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                        return ServerResponse.badRequest()
                                .bodyValue("Franchise name must not be empty");
                    }
                    return franchiseServicePort.createFranchise(franchiseMapper.toModel(dto))
                            .map(franchiseMapper::toDto)
                            .flatMap(responseDto -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(responseDto));
                });
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        try {
            Long id = Long.parseLong(request.pathVariable("id"));
            return request.bodyToMono(FranchiseUpdateRequestDto.class)
                    .flatMap(dto -> {
                        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                            return ServerResponse.badRequest().bodyValue("Franchise name must not be empty");
                        }
                        return franchiseServicePort.updateFranchiseName(id, dto.getName())
                                .map(franchiseMapper::toDto)
                                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
                    });
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Franchise ID must be a valid number");
        }
    }
}
