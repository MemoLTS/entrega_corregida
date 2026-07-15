package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.HorarioTiendaController;
import com.tiendat.tienda.dto.HorarioTiendaRequestDTO;
import com.tiendat.tienda.dto.HorarioTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.HorarioTiendaService;

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

@WebMvcTest(HorarioTiendaController.class)
class HorarioTiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HorarioTiendaService horarioTiendaService;

    @Test
    void crearHorarioTienda() throws Exception{
        HorarioTiendaRequestDTO request = new HorarioTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setDiaSemana("LUNES");

        HorarioTiendaResponseDTO response = new HorarioTiendaResponseDTO(1L, 1L, "LUNES", null, null, true);

        when(horarioTiendaService.crearHorarioTienda(any(HorarioTiendaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/HorarioTienda/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.diaSemana").value("LUNES"))
            .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void listarHorarioTienda() throws Exception{
        HorarioTiendaResponseDTO h1 = new HorarioTiendaResponseDTO(1L, 1L, "LUNES", null, null, true);
        HorarioTiendaResponseDTO h2 = new HorarioTiendaResponseDTO(2L, 1L, "MARTES", null, null, true);

        when(horarioTiendaService.listarHorarioTienda()).thenReturn(List.of(h1, h2));

        mockMvc.perform(get("/api/v1/HorarioTienda/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].diaSemana").value("LUNES"));
    }

    @Test
    void listarPorTiendaHorario() throws Exception{
        HorarioTiendaResponseDTO h1 = new HorarioTiendaResponseDTO(1L, 1L, "LUNES", null, null, true);

        when(horarioTiendaService.listarPorTiendaHorario(1L)).thenReturn(List.of(h1));

        mockMvc.perform(get("/api/v1/HorarioTienda/listar/tienda/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void modificarHorarioTienda() throws Exception{
        HorarioTiendaRequestDTO datos = new HorarioTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setDiaSemana("MIERCOLES");

        HorarioTiendaResponseDTO actualizada = new HorarioTiendaResponseDTO(1L, 1L, "MIERCOLES", null, null, true);

        when(horarioTiendaService.modificarHorarioTienda(eq(1L), any(HorarioTiendaRequestDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/HorarioTienda/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.diaSemana").value("MIERCOLES"));
    }

    @Test
    void desactivarHorarioTienda() throws Exception{
        HorarioTiendaResponseDTO desactivado = new HorarioTiendaResponseDTO(1L, 1L, "LUNES", null, null, false);

        when(horarioTiendaService.desactivarHorarioTienda(1L)).thenReturn(desactivado);

        mockMvc.perform(put("/api/v1/HorarioTienda/desactivar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void modificarHorarioTienda_noEncontrado() throws Exception{
        HorarioTiendaRequestDTO datos = new HorarioTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setDiaSemana("LUNES");

        when(horarioTiendaService.modificarHorarioTienda(eq(99L), any(HorarioTiendaRequestDTO.class)))
            .thenThrow(new RecursoNoEncontradoException("Horario de tienda con id 99 no encontrado"));

        mockMvc.perform(put("/api/v1/HorarioTienda/modificar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarHorarioTienda() throws Exception{
        doNothing().when(horarioTiendaService).eliminarHorarioTienda(1L);

        mockMvc.perform(delete("/api/v1/HorarioTienda/eliminar/1"))
            .andExpect(status().isNoContent());
    }

}
