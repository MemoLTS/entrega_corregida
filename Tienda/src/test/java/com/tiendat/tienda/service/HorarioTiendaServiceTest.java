package com.tiendat.tienda.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.dto.HorarioTiendaRequestDTO;
import com.tiendat.tienda.dto.HorarioTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.HorarioTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.HorarioTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;
import com.tiendat.tienda.service.HorarioTiendaService;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioTiendaServiceTest {

    @Mock
    private HorarioTiendaRepository horarioTiendaRepository;

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private HorarioTiendaService horarioTiendaService;

    private Tienda tiendaExistente(Long id){
        Tienda tienda = new Tienda();
        tienda.setIdTienda(id);
        return tienda;
    }

    @Test
    void crearHorarioTienda_debeAsignarActivoTrue() {
        HorarioTiendaRequestDTO request = new HorarioTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setDiaSemana("LUNES");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(horarioTiendaRepository.save(any(HorarioTienda.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioTienda.class));

        HorarioTiendaResponseDTO resultado = horarioTiendaService.crearHorarioTienda(request);

        assertTrue(resultado.getActivo());
        assertEquals(1L, resultado.getIdTienda());
        verify(horarioTiendaRepository, times(1)).save(any(HorarioTienda.class));
    }

    @Test
    void crearHorarioTienda_debeLanzarExcepcionCuandoTiendaNoExiste() {
        HorarioTiendaRequestDTO request = new HorarioTiendaRequestDTO();
        request.setIdTienda(99L);
        request.setDiaSemana("LUNES");

        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioTiendaService.crearHorarioTienda(request));
        verify(horarioTiendaRepository, never()).save(any());
    }

    @Test
    void listarHorarioTienda_debeRetornarTodosLosHorarios() {
        HorarioTienda h1 = new HorarioTienda();
        h1.setTienda(tiendaExistente(1L));
        HorarioTienda h2 = new HorarioTienda();
        h2.setTienda(tiendaExistente(1L));

        when(horarioTiendaRepository.findAll()).thenReturn(List.of(h1, h2));

        List<HorarioTiendaResponseDTO> resultado = horarioTiendaService.listarHorarioTienda();

        assertEquals(2, resultado.size());
        verify(horarioTiendaRepository, times(1)).findAll();
    }

    @Test
    void listarPorTiendaHorario_debeRetornarHorariosDeLaTienda() {
        HorarioTienda h1 = new HorarioTienda();
        h1.setTienda(tiendaExistente(1L));

        when(horarioTiendaRepository.findByTienda_IdTienda(1L)).thenReturn(List.of(h1));

        List<HorarioTiendaResponseDTO> resultado = horarioTiendaService.listarPorTiendaHorario(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdTienda());
    }

    @Test
    void modificarHorarioTienda_cuandoExiste_debeActualizarCampos() {
        HorarioTienda existente = new HorarioTienda();
        existente.setIdHorarioTienda(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setDiaSemana("LUNES");
        existente.setHoraApertura(LocalTime.of(9, 0));
        existente.setHoraCierre(LocalTime.of(18, 0));

        HorarioTiendaRequestDTO datos = new HorarioTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setDiaSemana("MIERCOLES");
        datos.setHoraApertura(LocalTime.of(10, 0));
        datos.setHoraCierre(LocalTime.of(20, 0));

        when(horarioTiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(horarioTiendaRepository.save(any(HorarioTienda.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioTienda.class));

        HorarioTiendaResponseDTO resultado = horarioTiendaService.modificarHorarioTienda(1L, datos);

        assertEquals("MIERCOLES", resultado.getDiaSemana());
        assertEquals(LocalTime.of(10, 0), resultado.getHoraApertura());
        assertEquals(LocalTime.of(20, 0), resultado.getHoraCierre());
    }

    @Test
    void modificarHorarioTienda_cuandoNoExiste_debeLanzarExcepcion() {
        when(horarioTiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioTiendaService.modificarHorarioTienda(99L, new HorarioTiendaRequestDTO()));
        verify(horarioTiendaRepository, never()).save(any());
    }

    @Test
    void desactivarHorarioTienda_cuandoExiste_debeCambiarActivoAFalse() {
        HorarioTienda existente = new HorarioTienda();
        existente.setIdHorarioTienda(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setActivo(true);

        when(horarioTiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(horarioTiendaRepository.save(any(HorarioTienda.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioTienda.class));

        HorarioTiendaResponseDTO resultado = horarioTiendaService.desactivarHorarioTienda(1L);

        assertFalse(resultado.getActivo());
    }

    @Test
    void desactivarHorarioTienda_cuandoNoExiste_debeLanzarExcepcion() {
        when(horarioTiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioTiendaService.desactivarHorarioTienda(99L));
    }

    @Test
    void eliminarHorarioTienda_debeInvocarDeleteById() {
        when(horarioTiendaRepository.existsById(1L)).thenReturn(true);

        horarioTiendaService.eliminarHorarioTienda(1L);

        verify(horarioTiendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarHorarioTienda_debeLanzarExcepcionCuandoNoExiste() {
        when(horarioTiendaRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> horarioTiendaService.eliminarHorarioTienda(99L));
        verify(horarioTiendaRepository, never()).deleteById(any());
    }
}
