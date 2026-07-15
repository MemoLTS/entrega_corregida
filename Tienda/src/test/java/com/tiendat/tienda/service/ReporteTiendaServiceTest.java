package com.tiendat.tienda.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.dto.ReporteTiendaRequestDTO;
import com.tiendat.tienda.dto.ReporteTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.ReporteTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ReporteTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;
import com.tiendat.tienda.service.ReporteTiendaService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteTiendaServiceTest {

    @Mock
    private ReporteTiendaRepository reporteTiendaRepository;

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private ReporteTiendaService reporteTiendaService;

    private Tienda tiendaExistente(Long id){
        Tienda tienda = new Tienda();
        tienda.setIdTienda(id);
        return tienda;
    }

    @Test
    void crearReporteTienda_debeAsignarFechaGeneracion() {
        ReporteTiendaRequestDTO request = new ReporteTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setTipoReporte("VENTAS");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(reporteTiendaRepository.save(any(ReporteTienda.class)))
                .thenAnswer(inv -> inv.getArgument(0, ReporteTienda.class));

        ReporteTiendaResponseDTO resultado = reporteTiendaService.crearReporteTienda(request);

        assertNotNull(resultado.getFechaGeneracion());
        verify(reporteTiendaRepository, times(1)).save(any(ReporteTienda.class));
    }

    @Test
    void crearReporteTienda_debeLanzarExcepcionCuandoTiendaNoExiste() {
        ReporteTiendaRequestDTO request = new ReporteTiendaRequestDTO();
        request.setIdTienda(99L);
        request.setTipoReporte("VENTAS");

        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> reporteTiendaService.crearReporteTienda(request));
        verify(reporteTiendaRepository, never()).save(any());
    }

    @Test
    void listarReporteTenda(){
        ReporteTienda r1 = new ReporteTienda();
        r1.setTienda(tiendaExistente(1L));
        ReporteTienda r2 = new ReporteTienda();
        r2.setTienda(tiendaExistente(1L));

        when(reporteTiendaRepository.findAll()).thenReturn(List.of(r1, r2));

        List<ReporteTiendaResponseDTO> resultado = reporteTiendaService.listarReporteTienda();

        assertEquals(2, resultado.size());
        verify(reporteTiendaRepository, times(1)).findAll();

    }

    @Test
    void listarPorTiendaReporte(){
        ReporteTienda r1 = new ReporteTienda();
        r1.setTienda(tiendaExistente(1L));

        when(reporteTiendaRepository.findByTienda_IdTienda(1L)).thenReturn(List.of(r1));

        List<ReporteTiendaResponseDTO> resultado = reporteTiendaService.listarPorTiendaReporte(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdTienda());
    }

    @Test
    void modificarReporte(){
        ReporteTienda existente = new ReporteTienda();
        existente.setIdReporte(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setTipoReporte("VENTAS");

        ReporteTiendaRequestDTO datos = new ReporteTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setTipoReporte("INVENTARIO");

        when(reporteTiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(reporteTiendaRepository.save(any(ReporteTienda.class)))
                .thenAnswer(inv -> inv.getArgument(0, ReporteTienda.class));

        ReporteTiendaResponseDTO resultado = reporteTiendaService.modificarReporte(1L, datos);

        assertEquals("INVENTARIO", resultado.getTipoReporte());
    }

    @Test
    void modificarReporte_debeLanzarExcepcionCuandoNoExiste(){
        when(reporteTiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> reporteTiendaService.modificarReporte(99L, new ReporteTiendaRequestDTO()));
        verify(reporteTiendaRepository, never()).save(any());
    }

    @Test
    void eliminarReporte(){
        when(reporteTiendaRepository.existsById(1L)).thenReturn(true);

        reporteTiendaService.eliminarReporte(1L);

        verify(reporteTiendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarReporte_debeLanzarExcepcionCuandoNoExiste(){
        when(reporteTiendaRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> reporteTiendaService.eliminarReporte(99L));
        verify(reporteTiendaRepository, never()).deleteById(any());
    }

}
