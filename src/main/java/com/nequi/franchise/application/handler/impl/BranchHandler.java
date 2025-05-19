package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.dto.request.BranchUpdateRequestDto;
import com.nequi.franchise.application.handler.IBranchHandler;
import com.nequi.franchise.application.mapper.IBranchMapper;
import com.nequi.franchise.domain.api.IBranchServicePort;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.application.util.constants.BranchHandlerConstants.*;

@AllArgsConstructor
public class BranchHandler implements IBranchHandler {

    private IBranchServicePort branchServicePort;
    private IBranchMapper branchMapper;

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .flatMap(dto -> {
                    if (dto.getFranchiseId() == null ||
                            dto.getName() == null || dto.getName().isBlank() ||
                            dto.getAddress() == null || dto.getAddress().isBlank()) {

                        return ServerResponse.badRequest()
                                .bodyValue(ERROR_REQUIRED_FIELDS);
                    }

                    return branchServicePort.createBranch(branchMapper.toModel(dto))
                            .map(branchMapper::toDto)
                            .flatMap(res -> ServerResponse.ok().bodyValue(res));
                });
    }

    @Override
    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        try {
            Long id = Long.parseLong(request.pathVariable("id"));

            return request.bodyToMono(BranchUpdateRequestDto.class)
                    .flatMap(dto -> {
                        if (dto.getName() == null || dto.getName().isBlank()) {
                            return ServerResponse.badRequest().bodyValue(ERROR_REQUIRED_BRANCH_NAME);
                        }

                        return branchServicePort.updateBranchName(id, dto.getName())
                                .map(branchMapper::toDto)
                                .flatMap(res -> ServerResponse.ok().bodyValue(res));
                    });

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue(ERROR_INVALID_BRANCH_ID);
        }
    }
}
