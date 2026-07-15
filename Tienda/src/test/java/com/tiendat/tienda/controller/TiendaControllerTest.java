package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.TiendaController;
import com.tiendat.tienda.dto.TiendaRequestDTO;
import com.tiendat.tienda.dto.TiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.TiendaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TiendaController.class)
class TiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TiendaService tiendaService;

    @Test
    void crearTienda() throws Exception{
        TiendaRequestDTO request = new TiendaRequestDTO();
        request.setNombre("EcoMarket central");

        TiendaResponseDTO response = new TiendaResponseDTO(1L, "EcoMarket central", null, null, null, null, null, null, "ACTIVA", LocalDateTime.now());

        when(tiendaService.crearTienda(any(TiendaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/tienda/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.nombre").value("EcoMarket central"))
        .andExpect(jsonPath("$.estado").value("ACTIVA"));
    }

    @Test
    void listarTiendas() throws Exception{
        TiendaResponseDTO t1 = new TiendaResponseDTO(1L, "Tienda providencia", null, null, null, null, null, null, "ACTIVA", LocalDateTime.now());
        TiendaResponseDTO t2 = new TiendaResponseDTO(2L, "Tienda maipu", null, null, null, null, null, null, "ACTIVA", LocalDateTime.now());

        when(tiendaService.listarTiendas()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/v1/tienda/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Tienda providencia"));
    }

    @Test
    void buscarTiendaPorId() throws Exception{
        TiendaResponseDTO response = new TiendaResponseDTO(1L, "EcoMarket sur", null, null, null, null, null, null, "ACTIVA", LocalDateTime.now());

        when(tiendaService.buscarTiendaPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/tienda/buscar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("EcoMarket sur"));
    }

    @Test
    void buscarTiendaPorId_noEncontrada() throws Exception{ //Cuando no existe la tienda
        when(tiendaService.buscarTiendaPorId(99L))
            .thenThrow(new RecursoNoEncontradoException("Tienda con id 99 no encontrada"));

        mockMvc.perform(get("/api/v1/tienda/buscar/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Tienda con id 99 no encontrada"));
    }

    @Test
    void modificarTienda() throws Exception{ //Retornar 200 con los datos actualizados
        TiendaRequestDTO datos = new TiendaRequestDTO();
        datos.setNombre("EcoMarket sur plus");

        TiendaResponseDTO actualizada = new TiendaResponseDTO(1L, "EcoMarket sur plus", null, null, null, null, null, null, "ACTIVA", LocalDateTime.now());

        when(tiendaService.modificarTienda(eq(1L), any(TiendaRequestDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/tienda/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("EcoMarket sur plus"));
    }

    @Test
    void desactivarTienda() throws Exception{
        TiendaResponseDTO inactiva = new TiendaResponseDTO(1L, "EcoMarket sur", null, null, null, null, null, null, "INACTIVA", LocalDateTime.now());

        when(tiendaService.desactivarTienda(1L)).thenReturn(inactiva);

        mockMvc.perform(put("/api/v1/tienda/desactivar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").value("INACTIVA"));
    }

    @Test
    void eliminarTienda() throws Exception{
        doNothing().when(tiendaService).eliminarTienda(1L);

        mockMvc.perform(delete("/api/v1/tienda/eliminar/1"))
            .andExpect(status().isNoContent());
    }

}
