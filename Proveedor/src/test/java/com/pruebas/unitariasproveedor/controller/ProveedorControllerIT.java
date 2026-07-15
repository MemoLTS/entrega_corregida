package com.pruebas.unitariasproveedor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.pruebas.unitariasproveedor.model.Proveedor;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProveedorControllerIT {
    
     @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void deberiaCrearProveedor()
            throws Exception {

        Proveedor proveedor =
                new Proveedor();

        proveedor.setNombre(
                "Proveedor Test");

        proveedor.setContacto(
                "test@eco.cl");

        proveedor.setTelefono(
                "123456789");

        mockMvc.perform(

                post("/api/proveedores")

                        .contentType(
                                MediaType.APPLICATION_JSON)

                        .content(
                                mapper.writeValueAsString(
                                        proveedor))

        )
                .andExpect(
                        status().isOk())

                .andExpect(
                        jsonPath("$.nombre")
                                .value(
                                        "Proveedor Test"));
    }
}
