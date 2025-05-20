package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.dto.request.ProductUpdateRequestDto;
import com.nequi.franchise.application.dto.request.StockUpdateRequestDto;
import com.nequi.franchise.application.handler.IProductHandler;
import com.nequi.franchise.application.mapper.IProductMapper;
import com.nequi.franchise.domain.api.IProductServicePort;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.nequi.franchise.application.util.constants.ProductHandlerConstants.*;

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
                                .bodyValue(INVALID_PRODUCT_FIELDS );
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
            Long id = Long.parseLong(request.pathVariable(PATH_VARIABLE_ID));

            if (id <= MIN_VALID_ID) {
                return ServerResponse.badRequest()
                        .bodyValue(NEGATIVE_PRODUCT_ID);
            }

            return productServicePort.deleteProduct(id)
                    .then(ServerResponse.noContent().build());

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest()
                    .bodyValue(INVALID_PRODUCT_ID);
        }
    }

    @Override
    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable(PATH_VARIABLE_ID));
        return request.bodyToMono(StockUpdateRequestDto.class)
                .flatMap(dto -> {
                    if (dto.getStock() == null || dto.getStock() < MIN_VALID_STOCK) {
                        return ServerResponse.badRequest().bodyValue(INVALID_STOCK_VALUE );
                    }
                    return productServicePort.updateStock(id, dto.getStock())
                            .map(productMapper::toDto)
                            .flatMap(res -> ServerResponse.ok().bodyValue(res));
                });
    }

    @Override
    public Mono<ServerResponse> updateName(ServerRequest request) {
        try {
            Long id = Long.parseLong(request.pathVariable(PATH_VARIABLE_ID));

            return request.bodyToMono(ProductUpdateRequestDto.class)
                    .flatMap(dto -> {
                        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                            return ServerResponse.badRequest().bodyValue(INVALID_PRODUCT_NAME);
                        }
                        return productServicePort.updateName(id, dto.getName())
                                .map(productMapper::toDto)
                                .flatMap(res -> ServerResponse.ok().bodyValue(res));
                    });

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue(INVALID_PRODUCT_ID);
        }
    }

}
