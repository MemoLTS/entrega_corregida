package com.pruebas.unitarias.pagos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Microservicio Pagos - EcoMarket",
        version = "1.0",
        description = "API REST para la gestión de pagos del sistema EcoMarket"
    ),
    servers = @Server(url = "http://localhost:8083", description = "Servidor local")
)
public class OpenApiConfig {

}
