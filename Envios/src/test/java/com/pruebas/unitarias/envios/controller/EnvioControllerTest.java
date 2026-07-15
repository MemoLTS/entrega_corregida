package com.pruebas.unitarias.envios.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.envios.model.Envio;
import com.pruebas.unitarias.envios.repository.EnvioRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EnvioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnvioRepository repository;

    @Test
    void deberiaConsultarEnvio()
            throws Exception {

        Envio envio = new Envio();

        envio.setPedidoId(400L);
        envio.setDireccionDestino("Valparaíso");
        envio.setTransportista("EcoCargo");
        envio.setEstado("PENDIENTE");

        Envio guardado =
                repository.save(envio);

        mockMvc.perform(

                get("/api/envios/"
                        + guardado.getId())

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.direccionDestino")
                                .value("Valparaíso"));
    }
}
