package com.tiendat.tienda.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.tiendat.tienda.dto.ProductoDTO;

import reactor.core.publisher.Mono;

@Component
public class CatalogoClient {

    @Autowired
    private WebClient webClient;

    public ProductoDTO obtenerProductoPorId(Long idProducto){
        return webClient.get()
                .uri("/api/v1/catalogo/buscar/{id}", idProducto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Producto no encontrado en Catalogo: " + idProducto)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error en el microservicio Catalogo")))
                .bodyToMono(ProductoDTO.class)
                .block();
    }
    
}
