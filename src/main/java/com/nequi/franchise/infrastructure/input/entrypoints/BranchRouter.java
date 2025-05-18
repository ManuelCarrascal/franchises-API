package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.handler.IBranchHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class BranchRouter {
    @Bean
    @RouterOperation(
            path = "/api/branches",
            method = RequestMethod.POST,
            beanClass = IBranchHandler.class,
            beanMethod = "create",
            operation = @Operation(
                    summary = "Crear nueva sucursal"
    )
)
    public RouterFunction<ServerResponse> branchRoutes(IBranchHandler handler) {
        return route(POST("/api/branches"), handler::create);
    }
}
