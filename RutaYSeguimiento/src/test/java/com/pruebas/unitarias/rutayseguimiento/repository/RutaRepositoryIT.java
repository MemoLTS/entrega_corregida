package com.pruebas.unitarias.rutayseguimiento.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;

@DataJpaTest
public class RutaRepositoryIT {
    
    @Autowired
    private RutaRepository repository;

    @Test
    void deberiaGuardarRuta() {

        Ruta ruta = new Ruta();

        ruta.setEnvioId(100L);
        ruta.setOrigen("Santiago");
        ruta.setDestino("Valdivia");
        ruta.setUbicacionActual("Talca");
        ruta.setEstado("EN_RUTA");

        Ruta guardada =
                repository.save(ruta);

        assertNotNull(
                guardada.getId());

        assertEquals(
                "Valdivia",
                guardada.getDestino());
    }
}
