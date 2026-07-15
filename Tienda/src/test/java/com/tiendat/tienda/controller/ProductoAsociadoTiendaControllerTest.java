package com.tiendat.tienda.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendat.tienda.controller.ProductoAsociadoTiendaController;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaRequestDTO;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.service.ProductoAsociadoTiendaService;

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

@WebMvcTest(ProductoAsociadoTiendaController.class)
class ProductoAsociadoTiendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoAsociadoTiendaService productoAsociadoTiendaService;

    @Test
    void crearProductoAsociado() throws Exception{
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setIdProducto(1L);

        ProductoAsociadoTiendaResponseDTO response = new ProductoAsociadoTiendaResponseDTO(1L, 1L, 1L, "Manzana Roja", true);

        when(productoAsociadoTiendaService.crearProductoAsociado(any(ProductoAsociadoTiendaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/ProductoAsociadoTienda/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombreProducto").value("Manzana Roja"))
            .andExpect(jsonPath("$.visibleEnTienda").value(true));
    }

    @Test
    void listarTiendaProducto() throws Exception{
        ProductoAsociadoTiendaResponseDTO p1 = new ProductoAsociadoTiendaResponseDTO(1L, 1L, 1L, "Manzana Roja", true);
        ProductoAsociadoTiendaResponseDTO p2 = new ProductoAsociadoTiendaResponseDTO(2L, 1L, 2L, "Pera", true);

        when(productoAsociadoTiendaService.listarTiendaProducto()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/v1/ProductoAsociadoTienda/listar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombreProducto").value("Manzana Roja"));
    }

    @Test
    void listarPorTiendaProducto() throws Exception{
        ProductoAsociadoTiendaResponseDTO p1 = new ProductoAsociadoTiendaResponseDTO(1L, 1L, 1L, "Manzana Roja", true);

        when(productoAsociadoTiendaService.listarPorTiendaProducto(1L)).thenReturn(List.of(p1));

        mockMvc.perform(get("/api/v1/ProductoAsociadoTienda/listar/tienda/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void modificarProductoTienda() throws Exception{
        ProductoAsociadoTiendaRequestDTO datos = new ProductoAsociadoTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdProducto(1L);
        datos.setNombreProducto("Nombre nuevo");

        ProductoAsociadoTiendaResponseDTO actualizado = new ProductoAsociadoTiendaResponseDTO(1L, 1L, 1L, "Nombre nuevo", true);

        when(productoAsociadoTiendaService.modificarProductoTienda(eq(1L), any(ProductoAsociadoTiendaRequestDTO.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/ProductoAsociadoTienda/modificar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreProducto").value("Nombre nuevo"));
    }

    @Test
    void ocultarProductoAsociadoTienda() throws Exception{
        ProductoAsociadoTiendaResponseDTO oculto = new ProductoAsociadoTiendaResponseDTO(1L, 1L, 1L, "Manzana Roja", false);

        when(productoAsociadoTiendaService.ocultarProductoAsociadoTienda(1L)).thenReturn(oculto);

        mockMvc.perform(put("/api/v1/ProductoAsociadoTienda/ocultar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.visibleEnTienda").value(false));
    }

    @Test
    void modificarProductoTienda_noEncontrado() throws Exception{
        ProductoAsociadoTiendaRequestDTO datos = new ProductoAsociadoTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdProducto(1L);

        when(productoAsociadoTiendaService.modificarProductoTienda(eq(99L), any(ProductoAsociadoTiendaRequestDTO.class)))
            .thenThrow(new RecursoNoEncontradoException("Producto asociado con id 99 no encontrado"));

        mockMvc.perform(put("/api/v1/ProductoAsociadoTienda/modificar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datos)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarProductoAsociadoTienda() throws Exception{
        doNothing().when(productoAsociadoTiendaService).eliminarProductoAsociadoTienda(1L);

        mockMvc.perform(delete("/api/v1/ProductoAsociadoTienda/eliminar/1"))
            .andExpect(status().isNoContent());
    }

}
