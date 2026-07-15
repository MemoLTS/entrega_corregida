package com.pruebas.unitarias.pedido.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.pruebas.unitarias.pedido.model.Pedido;
import com.pruebas.unitarias.pedido.repository.PedidoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository repository;

    @Test
    void deberiaConsultarPedido()
            throws Exception {

        Pedido pedido = new Pedido();

        pedido.setCliente("Cliente Consulta");
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(50000.0);

        Pedido guardado =
                repository.save(pedido);

        mockMvc.perform(
                get("/api/pedidos/" + guardado.getId())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").value("Cliente Consulta"));
    }
}
