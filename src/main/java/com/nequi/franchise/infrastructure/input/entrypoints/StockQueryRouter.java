package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.handler.IStockQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.franchise.infrastructure.utils.constants.ResponseCodesOpenApiConstants.*;
import static com.nequi.franchise.infrastructure.utils.constants.StockQueryRouterConstants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@Tag(name = TAG, description = DESCRIPTION)
public class StockQueryRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_PATH,
                    method = RequestMethod.GET,
                    beanClass = IStockQueryHandler.class,
                    beanMethod = "getTopStockProducts",
                    operation = @Operation(
                            operationId = OPERATION_ID,
                            summary = OPERATION_SUMMARY,
                            description = OPERATION_DESCRIPTION,
                            parameters = {
                                    @Parameter(name = PARAM_ID_NAME, description = PARAM_ID_DESCRIPTION, required = true)
                            },
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_200, description = RESPONSE_OK_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_404, description = RESPONSE_NOT_FOUND_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_500, description = RESPONSE_ERROR_DESCRIPTION, content = @Content)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> stockRoutes(IStockQueryHandler handler) {
        return RouterFunctions.route(GET("/api/franchises/{id}/top-stock-products"), handler::getTopStockProducts);
    }
}
