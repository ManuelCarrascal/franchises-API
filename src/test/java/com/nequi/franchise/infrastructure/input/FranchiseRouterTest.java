package com.nequi.franchise.infrastructure.input;

import com.nequi.franchise.application.handler.IFranchiseHandler;
import com.nequi.franchise.infrastructure.input.entrypoints.FranchiseRouter;
import com.nequi.franchise.infrastructure.utils.constants.FranchiseRouterConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FranchiseRouterTest {

    @Test
    void shouldRouteToCreateFranchiseHandler() {
        IFranchiseHandler handler = Mockito.mock(IFranchiseHandler.class);

        when(handler.create(any(ServerRequest.class)))
                .thenReturn(ServerResponse.ok().build());

        FranchiseRouter router = new FranchiseRouter();
        RouterFunction<ServerResponse> routerFunction = router.franchiseRoutes(handler);

        WebTestClient client = WebTestClient.bindToRouterFunction(routerFunction).build();

        client.post()
                .uri(FranchiseRouterConstants.BASE_PATH)
                .bodyValue("{\"name\":\"Taco Bell\"}")
                .exchange()
                .expectStatus().isOk();
    }
}
