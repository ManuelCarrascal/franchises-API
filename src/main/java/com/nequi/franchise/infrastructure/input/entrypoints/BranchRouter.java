package com.nequi.franchise.infrastructure.input.entrypoints;

import com.nequi.franchise.application.dto.request.BranchRequestDto;
import com.nequi.franchise.application.dto.request.BranchUpdateRequestDto;
import com.nequi.franchise.application.handler.IBranchHandler;
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

import static com.nequi.franchise.infrastructure.utils.constants.BranchRouterConstants.*;
import static com.nequi.franchise.infrastructure.utils.constants.ResponseCodesOpenApiConstants.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Tag(name = TAG, description = DESCRIPTION )
public class BranchRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_PATH,
                    method = RequestMethod.POST,
                    beanClass = IBranchHandler.class,
                    beanMethod = "create",
                    operation = @Operation(
                            operationId = OPERATION_ID_CREATE,
                            summary = OPERATION_SUMMARY_CREATE,
                            description = OPERATION_DESCRIPTION_CREATE ,
                            requestBody = @RequestBody(
                                    required = true,
                                    description = REQUEST_BODY_DESCRIPTION,
                                    content = @Content(
                                            schema = @Schema(implementation = BranchRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = RESPONSE_200, description = RESPONSE_OK_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_400, description = RESPONSE_BAD_REQUEST_DESCRIPTION),
                                    @ApiResponse(responseCode = RESPONSE_404, description = RESPONSE_NOT_FOUND_DESCRIPTION)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/branches/{id}",
                    method = RequestMethod.PUT,
                    beanClass = IBranchHandler.class,
                    beanMethod = "update",
                    operation = @Operation(
                            operationId = "updateBranch",
                            summary = "Update a branch's name",
                            description = "Updates the name of a specific branch by ID",
                            parameters = @Parameter(name = "id", description = "Branch ID", required = true),
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BranchUpdateRequestDto.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Branch updated successfully"),
                                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                                    @ApiResponse(responseCode = "404", description = "Branch not found")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> branchRoutes(IBranchHandler handler) {
        return route()
                .POST(BASE_PATH, handler::create)
                .PUT(BASE_PATH+"/{id}", handler::update)
                .build();
    }
}
