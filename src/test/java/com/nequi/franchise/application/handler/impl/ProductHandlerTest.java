package com.nequi.franchise.application.handler.impl;

import com.nequi.franchise.application.dto.request.ProductRequestDto;
import com.nequi.franchise.application.dto.request.ProductUpdateRequestDto;
import com.nequi.franchise.application.dto.request.StockUpdateRequestDto;
import com.nequi.franchise.application.dto.response.ProductResponseDto;
import com.nequi.franchise.application.mapper.IProductMapper;
import com.nequi.franchise.domain.api.IProductServicePort;
import com.nequi.franchise.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.nequi.franchise.application.util.constants.ProductHandlerConstants.*;
import static org.mockito.Mockito.*;

class ProductHandlerTest {

    private IProductServicePort productServicePort;
    private IProductMapper productMapper;
    private ProductHandler handler;

    @BeforeEach
    void setUp() {
        productServicePort = mock(IProductServicePort.class);
        productMapper = mock(IProductMapper.class);
        handler = new ProductHandler(productServicePort, productMapper);
    }

    @Test
    void create_shouldReturnBadRequest_whenFieldsInvalid() {
        ProductRequestDto dto = new ProductRequestDto(null, null, null, null);

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(ProductRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.create(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();

        verifyNoInteractions(productServicePort);
    }

    @Test
    void create_shouldReturnOk_whenValid() {
        ProductRequestDto dto = new ProductRequestDto("Product A", 20.0, 10, 1L);
        Product model = new Product(null, "Product A", 20.0, 0, 1L);
        Product saved = new Product(1L, "Product A", 20.0, 0, 1L);
        ProductResponseDto responseDto = new ProductResponseDto(1L, "Product A", 20.0, 0, 1L);

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(ProductRequestDto.class)).thenReturn(Mono.just(dto));
        when(productMapper.toModel(dto)).thenReturn(model);
        when(productServicePort.createProduct(model)).thenReturn(Mono.just(saved));
        when(productMapper.toDto(saved)).thenReturn(responseDto);

        StepVerifier.create(handler.create(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void delete_shouldReturnBadRequest_whenIdInvalid() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("abc");

        StepVerifier.create(handler.delete(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void delete_shouldReturnBadRequest_whenNegativeId() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("-1");

        StepVerifier.create(handler.delete(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void delete_shouldReturnNoContent_whenValid() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("1");
        when(productServicePort.deleteProduct(1L)).thenReturn(Mono.empty());

        StepVerifier.create(handler.delete(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void updateStock_shouldReturnBadRequest_whenStockInvalid() {
        StockUpdateRequestDto dto = new StockUpdateRequestDto(-5);

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("1");
        when(request.bodyToMono(StockUpdateRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.updateStock(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateStock_shouldReturnOk_whenValid() {
        StockUpdateRequestDto dto = new StockUpdateRequestDto(10);
        Product updated = new Product(1L, "Product A", 20.0, 10, 1L);
        ProductResponseDto response = new ProductResponseDto(1L, "Product A", 20.0, 10, 1L);

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("1");
        when(request.bodyToMono(StockUpdateRequestDto.class)).thenReturn(Mono.just(dto));
        when(productServicePort.updateStock(1L, 10)).thenReturn(Mono.just(updated));
        when(productMapper.toDto(updated)).thenReturn(response);

        StepVerifier.create(handler.updateStock(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void updateName_shouldReturnBadRequest_whenIdInvalid() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("xyz");

        StepVerifier.create(handler.updateName(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateName_shouldReturnBadRequest_whenNameEmpty() {
        ProductUpdateRequestDto dto = new ProductUpdateRequestDto("   ");
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("1");
        when(request.bodyToMono(ProductUpdateRequestDto.class)).thenReturn(Mono.just(dto));

        StepVerifier.create(handler.updateName(request))
                .expectNextMatches(res -> res.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateName_shouldReturnOk_whenValid() {
        ProductUpdateRequestDto dto = new ProductUpdateRequestDto("Nuevo");
        Product product = new Product(1L, "Nuevo", 20.0, 5, 1L);
        ProductResponseDto response = new ProductResponseDto(1L, "Nuevo", 20.0, 5, 1L);

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable(PATH_VARIABLE_ID)).thenReturn("1");
        when(request.bodyToMono(ProductUpdateRequestDto.class)).thenReturn(Mono.just(dto));
        when(productServicePort.updateName(1L, "Nuevo")).thenReturn(Mono.just(product));
        when(productMapper.toDto(product)).thenReturn(response);

        StepVerifier.create(handler.updateName(request))
                .expectNextMatches(res -> res.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
}
