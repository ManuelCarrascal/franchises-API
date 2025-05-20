package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.dto.request.FranchiseRequestDto;
import com.nequi.franchise.application.dto.request.FranchiseUpdateRequestDto;
import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.infrastructure.utils.constants.FranchiseRouterConstants;
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
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.nequi.franchise.infrastructure.utils.constants.FranchiseRouterConstants.*;
import static com.nequi.franchise.infrastructure.utils.constants.ResponseCodesOpenApiConstants.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = FranchiseRouterConstants.TAG, description = FranchiseRouterConstants.DESCRIPTION)
public class FranchiseRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = FranchiseRouterConstants.BASE_PATH,
                    produces = {"application/json"},
                    method = RequestMethod.POST,
                    beanClass = IFranchiseHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = FranchiseRouterConstants.OPERATION_ID_CREATE,
                            summary = FranchiseRouterConstants.OPERATION_SUMMARY_CREATE,
                            description = FranchiseRouterConstants.OPERATION_DESCRIPTION_CREATE,
                            requestBody = @RequestBody(
                                    required = true,
                                    description = FranchiseRouterConstants.REQUEST_BODY_DESCRIPTION,
                                    content = @Content(
                                            schema = @Schema(implementation = FranchiseRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = RESPONSE_200,
                                            description = FranchiseRouterConstants.RESPONSE_OK_DESCRIPTION
                                    ),
                                    @ApiResponse(
                                            responseCode = RESPONSE_400,
                                            description = FranchiseRouterConstants.RESPONSE_BAD_REQUEST_DESCRIPTION
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/franchises/{id}",
                    method = RequestMethod.PUT,
                    beanClass = IFranchiseHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = OPERATION_ID_UPDATE,
                            summary = OPERATION_SUMMARY_UPDATE,
                            description = OPERATION_DESCRIPTION_UPDATE,
                            parameters = @Parameter(name = PARAM_FRANCHISE_ID,
                                    description = PARAM_FRANCHISE_ID_DESCRIPTION,
                                    required = true),
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = FranchiseUpdateRequestDto.class
                                    ))),
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_200,
                                            description = RESPONSE_OK_UPDATE_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_400,
                                            description = RESPONSE_BAD_REQUEST_UPDATE_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_404,
                                            description = RESPONSE_NOT_FOUND_DESCRIPTION)
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> franchiseRoutes(IFranchiseHandler handler) {
        return route()
                .POST(FranchiseRouterConstants.BASE_PATH,handler::create)
                .PUT(FranchiseRouterConstants.BASE_PATH + "/{id}", handler::updateFranchise)
                .build();
    }
}
