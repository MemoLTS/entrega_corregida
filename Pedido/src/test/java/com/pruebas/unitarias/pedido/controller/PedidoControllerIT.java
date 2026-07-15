package com.pruebas.unitarias.pedido.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.pedido.model.Pedido;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerIT {
   
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deberiaCrearPedido()
            throws Exception {

        Pedido pedido = new Pedido();

        pedido.setCliente("Cliente API");
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(35000.0);

        mockMvc.perform(

                post("/api/pedidos")

                        .contentType(
                                MediaType.APPLICATION_JSON)

                        .content(
                                mapper.writeValueAsString(
                                        pedido))

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.cliente")
                                .value(
                                        "Cliente API"));
    }
}
