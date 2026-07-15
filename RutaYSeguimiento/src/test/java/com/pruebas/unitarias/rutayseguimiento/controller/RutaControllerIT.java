package com.pruebas.unitarias.rutayseguimiento.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RutaControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deberiaCrearRuta()
            throws Exception {

        Ruta ruta = new Ruta();

        ruta.setEnvioId(300L);
        ruta.setOrigen("Santiago");
        ruta.setDestino("La Serena");

        mockMvc.perform(

                post("/api/rutas")

                        .contentType(
                                MediaType.APPLICATION_JSON)

                        .content(
                                mapper.writeValueAsString(ruta))

        )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.destino")
                                .value("La Serena"));
    }
}
