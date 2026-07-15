package com.pruebas.unitarias.pagos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pruebas.unitarias.pagos.model.Pago;
import com.pruebas.unitarias.pagos.repository.PagoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PagoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PagoRepository repository;

    @Test
    void deberiaConsultarPago()
            throws Exception {

        Pago pago = new Pago();

        pago.setFacturaId(400L);
        pago.setMonto(75000.0);
        pago.setMetodoPago("TRANSFERENCIA");
        pago.setEstado("REGISTRADO");

        Pago guardado =
                repository.save(pago);

        mockMvc.perform(

                get("/api/pagos/"
                        + guardado.getId())

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.monto")
                                .value(75000.0));
    }
}
