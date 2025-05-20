package com.nequi.franchise.infrastructure.exceptionhandler;

import com.nequi.franchise.domain.exception.InvalidFranchiseDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
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
        String errorMessage = "Invalid name";
        Throwable exception = new InvalidFranchiseDataException(errorMessage);
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.post("/api/franchises").build()
        );

        Mono<Void> result = handler.handle(exchange, exception);

        StepVerifier.create(result).verifyComplete();

        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String expectedJson = String.format("{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);

        assertThat(response.getBodyAsString().block()).isEqualTo(expectedJson);
    }

    @Test
    void handle_shouldReturnInternalServerErrorForGenericException() {
        Throwable exception = new RuntimeException("Something went wrong");
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.post("/api/franchises").build()
        );

        Mono<Void> result = handler.handle(exchange, exception);

        StepVerifier.create(result).verifyComplete();

        MockServerHttpResponse response = (MockServerHttpResponse) exchange.getResponse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        String expectedJson = String.format("{\"status\": %d, \"error\": \"%s\", \"message\": \"%s\"}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error");

        assertThat(response.getBodyAsString().block()).isEqualTo(expectedJson);
    }
}
