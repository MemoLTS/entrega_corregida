package com.pruebas.unitarias.pagos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.pruebas.unitarias.pagos.model.Pago;

@DataJpaTest
public class PagoRepositoryIT {
    
     @Autowired
    private PagoRepository repository;

    @Test
    void deberiaGuardarPago() {

        Pago pago = new Pago();

        pago.setFacturaId(100L);
        pago.setMonto(25000.0);
        pago.setMetodoPago("TARJETA");
        pago.setEstado("REGISTRADO");

        Pago guardado =
                repository.save(pago);

        assertNotNull(
                guardado.getId());

        assertEquals(
                "TARJETA",
                guardado.getMetodoPago());
    }
}
