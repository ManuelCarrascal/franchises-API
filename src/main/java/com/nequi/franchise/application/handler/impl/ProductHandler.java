package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.handler.IProductHandler;
import com.nequi.franchise.application.mapper.IProductMapper;
import com.nequi.franchise.domain.api.IProductServicePort;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductHandler implements IProductHandler {

    private IProductServicePort productServicePort;
    private IProductMapper  productMapper;

    @Override
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .flatMap(dto -> {
                    if (dto.getName() == null || dto.getPrice() == null || dto.getBranchId() == null) {
                        return ServerResponse.badRequest()
                                .bodyValue("All product fields are required");
                    }
                    return productServicePort.createProduct(productMapper.toModel(dto))
                            .map(productMapper::toDto)
                            .flatMap(res -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(res));
                });
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        try {
            Long id = Long.parseLong(request.pathVariable("id"));

            if (id <= 0) {
                return ServerResponse.badRequest()
                        .bodyValue("Product ID must be a positive number");
            }

            return productServicePort.deleteProduct(id)
                    .then(ServerResponse.noContent().build());

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest()
                    .bodyValue("Product ID must be a valid number");
        }
    }
}
