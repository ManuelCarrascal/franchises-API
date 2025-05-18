package com.nequi.franchise.infrastructure.configuration;

import com.nequi.franchise.infrastructure.exceptionhandler.GlobalErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;

@Configuration
public class ErrorHandlerConfig {

    @Bean
    @Order(-2)
    public ErrorWebExceptionHandler globalErrorWebExceptionHandler() {
        return new GlobalErrorWebExceptionHandler();
    }
}
