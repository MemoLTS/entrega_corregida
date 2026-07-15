package com.tiendat.tienda.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.tiendat.tienda.client.CatalogoClient;
import com.tiendat.tienda.dto.ProductoDTO;

import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SuppressWarnings({"unchecked", "rawtypes", "unused"})
@ExtendWith(MockitoExtension.class)
class CatalogoClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CatalogoClient catalogoClient;

    @Test
    void obtenerProductoPorId() {
        ProductoDTO producto = new ProductoDTO(1L, "Manzana", "Fruta fresca", 1.5, "activo");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), (Object) any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductoDTO.class)).thenReturn(Mono.just(producto));

        ProductoDTO resultado = catalogoClient.obtenerProductoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Manzana", resultado.getNombre());
    }

    @Test
    void obtenerProductoPorId_errores() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), (Object) any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        doAnswer(inv -> {
            Function<ClientResponse, Mono<?>> fn = (Function<ClientResponse, Mono<?>>) inv.getArgument(1);
            fn.apply(mock(ClientResponse.class));
            return responseSpec;
        }).when(responseSpec).onStatus(any(), any());
        when(responseSpec.bodyToMono(ProductoDTO.class)).thenReturn(Mono.empty());

        assertNull(catalogoClient.obtenerProductoPorId(1L));
    }
}
