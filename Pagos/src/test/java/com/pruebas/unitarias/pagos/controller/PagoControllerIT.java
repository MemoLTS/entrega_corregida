package com.pruebas.unitarias.pagos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.pagos.model.Pago;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PagoControllerIT {
    
     @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deberiaRegistrarPago()
            throws Exception {

        Pago pago = new Pago();

        pago.setFacturaId(300L);
        pago.setMonto(50000.0);
        pago.setMetodoPago("TARJETA");

        mockMvc.perform(

                post("/api/pagos")

                        .contentType(
                                MediaType.APPLICATION_JSON)

                        .content(
                                mapper.writeValueAsString(
                                        pago))

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.metodoPago")
                                .value("TARJETA"));
    }
}
