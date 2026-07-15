package com.pruebas.unitarias.envios.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.pruebas.unitarias.envios.model.Envio;

@DataJpaTest
public class EnvioRepositoryIT {
    
    @Autowired
    private EnvioRepository repository;

    @Test
    void deberiaGuardarEnvio() {

        Envio envio = new Envio();

        envio.setPedidoId(100L);
        envio.setDireccionDestino("Santiago Centro");
        envio.setTransportista("EcoTransport");
        envio.setEstado("PENDIENTE");

        Envio guardado =
                repository.save(envio);

        assertNotNull(
                guardado.getId());

        assertEquals(
                "EcoTransport",
                guardado.getTransportista());
    }
}
