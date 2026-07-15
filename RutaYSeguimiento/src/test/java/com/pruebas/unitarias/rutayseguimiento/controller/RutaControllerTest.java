package com.pruebas.unitarias.rutayseguimiento.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;
import com.pruebas.unitarias.rutayseguimiento.repository.RutaRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RutaControllerTest {
    
     @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RutaRepository repository;

    @Test
    void deberiaConsultarRuta()
            throws Exception {

        Ruta ruta = new Ruta();

        ruta.setEnvioId(400L);
        ruta.setOrigen("Valdivia");
        ruta.setDestino("Puerto Montt");
        ruta.setEstado("EN_RUTA");

        Ruta guardada =
                repository.save(ruta);

        mockMvc.perform(
                get("/api/rutas/"
                        + guardada.getId())
        )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.destino")
                                .value("Puerto Montt"));
    }
}
