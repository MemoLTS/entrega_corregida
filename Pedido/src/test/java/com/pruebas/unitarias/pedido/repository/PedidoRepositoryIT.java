package com.pruebas.unitarias.pedido.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.pruebas.unitarias.pedido.model.Pedido;

@DataJpaTest
public class PedidoRepositoryIT {
    
    @Autowired
    private PedidoRepository repository;

    @Test
    void deberiaGuardarPedido() {

        Pedido pedido = new Pedido();

        pedido.setCliente("Juan Pérez");
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(25000.0);

        Pedido guardado =
                repository.save(pedido);

        assertNotNull(
                guardado.getId());

        assertEquals(
                "Juan Pérez",
                guardado.getCliente());
    }
}
