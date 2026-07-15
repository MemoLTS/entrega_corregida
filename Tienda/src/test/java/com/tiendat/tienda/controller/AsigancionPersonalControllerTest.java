package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.AsignacionPersonalController;
import com.tiendat.tienda.dto.AsignacionPersonalRequestDTO;
import com.tiendat.tienda.dto.AsignacionPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.AsignacionPersonalService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("unused")
@WebMvcTest(AsignacionPersonalController.class)
class AsigancionPersonalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AsignacionPersonalService asignacionPersonalService;

    @Test
    void crearAsignacionP() throws Exception{
        AsignacionPersonalRequestDTO request = new AsignacionPersonalRequestDTO();
        request.setIdTienda(1L);
        request.setIdEmpleado(1L);
        request.setNombreEmpleado("Juan Perez");
        request.setCargo("Vendedor");

        AsignacionPersonalResponseDTO response = new AsignacionPersonalResponseDTO(1L, 1L, 1L, "Juan Perez", "Vendedor", null, null, "ACTIVA");

        when(asignacionPersonalService.crearAsignacionP(any(AsignacionPersonalRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/AsignacionPersonal/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombreEmpleado").value("Juan Perez"))
            .andExpect(jsonPath("$.estadoAsignacion").value("ACTIVA"));
    }

    @Test
    void listarAsignacionP() throws Exception{
        AsignacionPersonalResponseDTO a1 = new AsignacionPersonalResponseDTO(1L, 1L, 1L, "Juan Perez", "Vendedor", null, null, "ACTIVA");
        AsignacionPersonalResponseDTO a2 = new AsignacionPersonalResponseDTO(2L, 1L, 2L, "Maria Lopez", "Cajera", null, null, "ACTIVA");

        when(asignacionPersonalService.listarAsignacionPersonal()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/v1/AsignacionPersonal/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombreEmpleado").value("Juan Perez"));
    }

    @Test
    void listarPorTiendaAsignacionP() throws Exception{
        AsignacionPersonalResponseDTO a1 = new AsignacionPersonalResponseDTO(1L, 1L, 1L, "Juan Perez", "Vendedor", null, null, "ACTIVA");

        when(asignacionPersonalService.listarPorTiendaAsignacionP(1L)).thenReturn(List.of(a1));

        mockMvc.perform(get("/api/v1/AsignacionPersonal/listar/tienda/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void modificarAsignacionP() throws Exception{
        AsignacionPersonalRequestDTO datos = new AsignacionPersonalRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdEmpleado(1L);
        datos.setNombreEmpleado("Nombre nuevo");
        datos.setCargo("Gerente");

        AsignacionPersonalResponseDTO actualizada = new AsignacionPersonalResponseDTO(1L, 1L, 1L, "Nombre nuevo", "Mantenimiento", null, null, "ACTIVA");

        when(asignacionPersonalService.modificarAsignacionP(eq(1L), any(AsignacionPersonalRequestDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/AsignacionPersonal/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreEmpleado").value("Nombre nuevo"))
            .andExpect(jsonPath("$.cargo").value("Mantenimiento"));
    }

    @Test
    void desactivarAsignacionP() throws Exception{
        AsignacionPersonalResponseDTO inactiva = new AsignacionPersonalResponseDTO(1L, 1L, 1L, "Juan Perez", "Vendedor", null, null, "INACTIVA");

        when(asignacionPersonalService.desactivarAsignacionP(1L)).thenReturn(inactiva);

        mockMvc.perform(put("/api/v1/AsignacionPersonal/desactivar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estadoAsignacion").value("INACTIVA"));
    }

    @Test
    void modificarAsignacionP_noEncontrada() throws Exception{
        AsignacionPersonalRequestDTO datos = new AsignacionPersonalRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdEmpleado(1L);
        datos.setNombreEmpleado("Nombre");
        datos.setCargo("Cargo");

        when(asignacionPersonalService.modificarAsignacionP(eq(99L), any(AsignacionPersonalRequestDTO.class)))
            .thenThrow(new RecursoNoEncontradoException("Asignacion con id 99 no encontrada"));

        mockMvc.perform(put("/api/v1/AsignacionPersonal/modificar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarAsignacionP() throws Exception{
        doNothing().when(asignacionPersonalService).eliminarAsignacionP(1L);

        mockMvc.perform(delete("/api/v1/AsignacionPersonal/eliminar/1"))
            .andExpect(status().isNoContent());
    }
}
