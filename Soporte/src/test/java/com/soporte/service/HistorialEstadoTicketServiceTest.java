package com.soporte.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soporte.soportem.exception.ResourceNotFoundException;
import com.soporte.soportem.model.HistorialEstadoTicket;
import com.soporte.soportem.repository.HistorialEstadoTicketRepository;
import com.soporte.soportem.service.HistorialEstadoTicketService;

import jakarta.inject.Inject;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class HistorialEstadoTicketServiceTest {
    
    @Mock 
    private HistorialEstadoTicketRepository historialEstadoTicketRepository;

    @InjectMocks
    private HistorialEstadoTicketService historialEstadoTicketService;

    @Test
    void listarPorIdTicket(){
        HistorialEstadoTicket h1 = HistorialEstadoTicket.builder()
                .idHistorial(1L).estadoNuevo("EN_PROCESO").usuarioResponsable("admin").build();
        HistorialEstadoTicket h2 = HistorialEstadoTicket.builder()
                .idHistorial(2L).estadoNuevo("CERRADO").usuarioResponsable("admin").build();
        when(historialEstadoTicketRepository
                .findByTicketSoporteIdTicketOrderByFechaCambioAsc(1L))
                .thenReturn(List.of(h1, h2));

        List<HistorialEstadoTicket> resultado = historialEstadoTicketService.listarPorIdTicket(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerHistorialPorId(){
        HistorialEstadoTicket historial = HistorialEstadoTicket.builder()
                .idHistorial(1L).estadoNuevo("CERRADO").build();
        when(historialEstadoTicketRepository.findById(1L)).thenReturn(Optional.of(historial));

        HistorialEstadoTicket resultado = historialEstadoTicketService.obtenerHistorialPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdHistorial());
    }

    @Test
    void obtenerHistorialPorId_noExiste(){
        when(historialEstadoTicketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> historialEstadoTicketService.obtenerHistorialPorId(99L));
    }
}
