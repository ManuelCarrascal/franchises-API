package com.nequi.franchise.infrastructure.exceptionhandler;

import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalErrorWebExceptionHandlerTest {

    private GlobalErrorWebExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalErrorWebExceptionHandler();
    }

    @Test
    void handle_shouldReturnBadRequestForInvalidFranchiseDataException() {
        // Arrange
        String errorMessage = "Nombre inválido";
        Throwable exception = new InvalidFranchiseDataException(errorMessage);
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.post("/api/franchises").build()
        );

        // Act
        Mono<Void> result = handler.handle(exchange, exception);

        // Assert
        StepVerifier.create(result).verifyComplete();

        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBodyAsString().block()).isEqualTo(errorMessage);
    }

    @Test
    void handle_shouldReturnInternalServerErrorForGenericException() {
        // Arrange
        Throwable exception = new RuntimeException("Algo salió mal");
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.post("/api/franchises").build()
        );

        // Act
        Mono<Void> result = handler.handle(exchange, exception);

        // Assert
        StepVerifier.create(result).verifyComplete();

        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBodyAsString().block()).isEqualTo("Unexpected error");
    }
}
