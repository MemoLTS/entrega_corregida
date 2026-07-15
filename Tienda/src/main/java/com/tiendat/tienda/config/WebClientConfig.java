package com.tiendat.tienda.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${catalogo.base-url:http://localhost:8085}")
    private String catalogoBaseUrl;

    @Bean
    public WebClient webClient(){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofSeconds(3));

        return WebClient.builder()
            //Se conecta al microservicio de catalogo
            .baseUrl(catalogoBaseUrl)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

}
