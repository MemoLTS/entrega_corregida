package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.ReporteTiendaController;
import com.tiendat.tienda.dto.ReporteTiendaRequestDTO;
import com.tiendat.tienda.dto.ReporteTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.ReporteTiendaService;

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

@WebMvcTest(ReporteTiendaController.class)
class ReporteTiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReporteTiendaService reporteTiendaService;

    @Test
    void crearReporteTienda() throws Exception{
        ReporteTiendaRequestDTO request = new ReporteTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setTipoReporte("VENTAS");

        ReporteTiendaResponseDTO response = new ReporteTiendaResponseDTO(1L, 1L, "VENTAS", null, null, null);

        when(reporteTiendaService.crearReporteTienda(any(ReporteTiendaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/ReporteTienda/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoReporte").value("VENTAS"));
    }

    @Test
    void listarReporteTienda() throws Exception{
        ReporteTiendaResponseDTO r1 = new ReporteTiendaResponseDTO(1L, 1L, "VENTAS", null, null, null);
        ReporteTiendaResponseDTO r2 = new ReporteTiendaResponseDTO(2L, 1L, "INVENTARIO", null, null, null);

        when(reporteTiendaService.listarReporteTienda()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/v1/ReporteTienda/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].tipoReporte").value("VENTAS"));
    }

    @Test
    void listarPorTiendaReporte() throws Exception{
        ReporteTiendaResponseDTO r1 = new ReporteTiendaResponseDTO(1L, 1L, "VENTAS", null, null, null);
        when(reporteTiendaService.listarPorTiendaReporte(1L)).thenReturn(List.of(r1));

        mockMvc.perform(get("/api/v1/ReporteTienda/listar/tienda/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void modificarReporte() throws Exception{
        ReporteTiendaRequestDTO datos = new ReporteTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setTipoReporte("INVENTARIO");

        ReporteTiendaResponseDTO actualizado = new ReporteTiendaResponseDTO(1L, 1L, "INVENTARIO", null, null, null);

        when(reporteTiendaService.modificarReporte(eq(1L), any(ReporteTiendaRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/ReporteTienda/modificar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoReporte").value("INVENTARIO"));
    }

    @Test
    void modificarReporte_noEncontrado() throws Exception{
        ReporteTiendaRequestDTO datos = new ReporteTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setTipoReporte("VENTAS");

        when(reporteTiendaService.modificarReporte(eq(99L), any(ReporteTiendaRequestDTO.class)))
            .thenThrow(new RecursoNoEncontradoException("Reporte con id 99 no encontrado"));

        mockMvc.perform(put("/api/v1/ReporteTienda/modificar/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarReporte() throws Exception{
        doNothing().when(reporteTiendaService).eliminarReporte(1L);

        mockMvc.perform(delete("/api/v1/ReporteTienda/eliminar/1"))
            .andExpect(status().isNoContent());
    }

}
