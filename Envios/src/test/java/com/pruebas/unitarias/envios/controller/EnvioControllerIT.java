package com.pruebas.unitarias.envios.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.envios.model.Envio;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnvioControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deberiaCrearEnvio()
            throws Exception {

        Envio envio = new Envio();

        envio.setPedidoId(300L);
        envio.setDireccionDestino("Antofagasta");
        envio.setTransportista("EcoCargo");

        mockMvc.perform(

                post("/api/envios")

                        .contentType(
                                MediaType.APPLICATION_JSON)

                        .content(
                                mapper.writeValueAsString(
                                        envio))

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.direccionDestino")
                                .value("Antofagasta"));
    }
}
