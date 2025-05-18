package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.dto.request.StockUpdateRequestDto;
import com.nequi.franchise.application.handler.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.franchise.infrastructure.utils.constants.ProductRouterConstants.*;
import static com.nequi.franchise.infrastructure.utils.constants.ResponseCodesOpenApiConstants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = TAG, description = DESCRIPTION)
public class ProductRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_PATH,
                    method = RequestMethod.POST,
                    beanClass = IProductHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = OPERATION_ID_CREATE,
                            summary = OPERATION_SUMMARY_CREATE,
                            description = OPERATION_DESCRIPTION_CREATE,
                            requestBody = @RequestBody(
                                    required = true,
                                    description = REQUEST_BODY_DESCRIPTION,
                                    content = @Content(schema = @Schema(implementation = ProductRequestDto.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_200, description = RESPONSE_OK_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_400, description = RESPONSE_BAD_REQUEST_DESCRIPTION)
                            }
                    )
            ),
            @RouterOperation(
                    path = BASE_PATH + "/{id}",
                    method = RequestMethod.DELETE,
                    beanClass = IProductHandler.class,
                    beanMethod = "delete",
                    operation = @Operation(
                            operationId = OPERATION_ID_DELETE,
                            summary = OPERATION_SUMMARY_DELETE,
                            description = OPERATION_DESCRIPTION_DELETE,
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_204, description = RESPONSE_NO_CONTENT_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_404, description = RESPONSE_NOT_FOUND_DESCRIPTION)
                            }
                    )
            ),
            @RouterOperation(
                    path = BASE_PATH+"/{id}/stock",
                    method = RequestMethod.PUT,
                    beanClass = IProductHandler.class,
                    beanMethod = "updateStock",
                    operation = @Operation(
                            operationId = OPERATION_ID_UPDATE_STOCK,
                            summary = OPERATION_SUMMARY_UPDATE_STOCK,
                            description = OPERATION_DESCRIPTION_UPDATE_STOCK,
                            parameters = @Parameter(name = PARAMETER_ID_NAME, description = PARAMETER_ID_DESCRIPTION, required = true),
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = StockUpdateRequestDto.class))),
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_200, description = RESPONSE_STOCK_UPDATED_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_400, description = RESPONSE_INVALID_STOCK_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_404, description = RESPONSE_NOT_FOUND_DESCRIPTION)
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> productRoutes(IProductHandler handler) {
        return RouterFunctions.route()
                .POST(BASE_PATH, handler::create)
                .DELETE(BASE_PATH + "/{id}", handler::delete)
                .PUT(BASE_PATH + "/{id}/stock", handler::updateStock)
                .build();
    }
}