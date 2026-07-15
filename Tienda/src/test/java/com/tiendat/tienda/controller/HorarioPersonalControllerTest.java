package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.HorarioPersonalController;
import com.tiendat.tienda.dto.HorarioPersonalRequestDTO;
import com.tiendat.tienda.dto.HorarioPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.HorarioPersonalService;

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

@WebMvcTest(HorarioPersonalController.class)
class HorarioPersonalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HorarioPersonalService horarioPersonalService;

    @Test
    void crearHorarioP() throws Exception{
        HorarioPersonalRequestDTO request = new HorarioPersonalRequestDTO();
        request.setIdAsignacion(1L);
        request.setDiaSemana("LUNES");

        HorarioPersonalResponseDTO response = new HorarioPersonalResponseDTO(1L, 1L, "LUNES", null, null, null, true);

        when(horarioPersonalService.crear(any(HorarioPersonalRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/HorarioPersonal/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.diaSemana").value("LUNES"))
            .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void listarHorariosP() throws Exception{
        HorarioPersonalResponseDTO h1 = new HorarioPersonalResponseDTO(1L, 1L, "LUNES", null, null, null, true);
        HorarioPersonalResponseDTO h2 = new HorarioPersonalResponseDTO(2L, 1L, "MARTES", null, null, null, true);

        when(horarioPersonalService.listar()).thenReturn(List.of(h1, h2));

        mockMvc.perform(get("/api/v1/HorarioPersonal/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].diaSemana").value("LUNES"));
    }

    @Test
    void listarPorAsignacion() throws Exception{
        HorarioPersonalResponseDTO h1 = new HorarioPersonalResponseDTO(1L, 1L, "LUNES", null, null, null, true);

        when(horarioPersonalService.listarPorAsignacion(1L)).thenReturn(List.of(h1));

        mockMvc.perform(get("/api/v1/HorarioPersonal/listar/asignacion/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void modificarHorarioP() throws Exception{
        HorarioPersonalRequestDTO datos = new HorarioPersonalRequestDTO();
        datos.setIdAsignacion(1L);
        datos.setDiaSemana("VIERNES");
        datos.setTurno("TARDE");

        HorarioPersonalResponseDTO actualizado = new HorarioPersonalResponseDTO(1L, 1L, "VIERNES", null, null, "TARDE", true);

        when(horarioPersonalService.modificar(eq(1L), any(HorarioPersonalRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/HorarioPersonal/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.diaSemana").value("VIERNES"))
            .andExpect(jsonPath("$.turno").value("TARDE"));
    }

    @Test
    void desactivarHorarioP() throws Exception{
        HorarioPersonalResponseDTO desactivado = new HorarioPersonalResponseDTO(1L, 1L, "LUNES", null, null, null, false);

        when(horarioPersonalService.desactivar(1L)).thenReturn(desactivado);

        mockMvc.perform(put("/api/v1/HorarioPersonal/desactivar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void modificarHorarioP_noEncontrado() throws Exception{
        HorarioPersonalRequestDTO datos = new HorarioPersonalRequestDTO();
        datos.setIdAsignacion(1L);
        datos.setDiaSemana("LUNES");

        when(horarioPersonalService.modificar(eq(99L), any(HorarioPersonalRequestDTO.class)))
            .thenThrow(new RecursoNoEncontradoException("Horario de personal con id 99 no encontrado"));

        mockMvc.perform(put("/api/v1/HorarioPersonal/modificar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarHorarioP() throws Exception{
        doNothing().when(horarioPersonalService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/HorarioPersonal/eliminar/1"))
            .andExpect(status().isNoContent());
    }
}
