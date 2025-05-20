package com.nequi.franchise.infrastructure.exceptionhandler;

import com.nequi.franchise.domain.exception.*;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Unexpected error";

        if (ex instanceof InvalidFranchiseDataException
                || ex instanceof InvalidBranchDataException
                || ex  instanceof InvalidProductDataException
                || ex instanceof  InvalidStockException) {
            status = HttpStatus.BAD_REQUEST;
            message = ex.getMessage();
        }  else if (ex instanceof FranchiseNotFoundException || ex instanceof BranchNotFoundException || ex instanceof ProductNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseJson = String.format(
                "{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                status.value(), status.getReasonPhrase(), message
        );

        byte[] bytes = responseJson.getBytes(StandardCharsets.UTF_8);
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

}
