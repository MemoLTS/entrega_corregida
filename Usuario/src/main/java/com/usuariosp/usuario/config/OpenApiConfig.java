package com.usuariosp.usuario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Microservicio Usuario - EcoMarket",
        version = "1.0",
        description = "API REST para la gestión de usuarios, cuentas, direcciones de envío y métodos de pago del sistema EcoMarket"
    ),
    servers = @Server(url = "http://localhost:9090", description = "Servidor local")
)
public class OpenApiConfig {

}
