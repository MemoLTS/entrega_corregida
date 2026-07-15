package com.tiendat.tienda.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.dto.HorarioPersonalRequestDTO;
import com.tiendat.tienda.dto.HorarioPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.HorarioPersonal;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.HorarioPersonalRepository;
import com.tiendat.tienda.service.HorarioPersonalService;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorarioPersonalServiceTest {

    @Mock
    private HorarioPersonalRepository horarioPersonalRepository;

    @Mock
    private AsignacionPersonalRepository asignacionPersonalRepository;

    @InjectMocks
    private HorarioPersonalService horarioPersonalService;

    private AsignacionPersonal asignacionExistente(Long id){
        AsignacionPersonal asignacion = new AsignacionPersonal();
        asignacion.setIdAsignacion(id);
        return asignacion;
    }

    @Test
    void crear_Asignacion(){
        HorarioPersonalRequestDTO request = new HorarioPersonalRequestDTO();
        request.setIdAsignacion(1L);
        request.setDiaSemana("LUNES");

        when(asignacionPersonalRepository.findById(1L)).thenReturn(Optional.of(asignacionExistente(1L)));
        when(horarioPersonalRepository.save(any(HorarioPersonal.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioPersonal.class));

        HorarioPersonalResponseDTO resultado = horarioPersonalService.crear(request);

        assertTrue(resultado.getActivo());
        assertEquals(1L, resultado.getIdAsignacion());
        verify(horarioPersonalRepository, times(1)).save(any(HorarioPersonal.class));
    }

    @Test
    void crear_debeLanzarExcepcionCuandoAsignacionNoExiste(){
        HorarioPersonalRequestDTO request = new HorarioPersonalRequestDTO();
        request.setIdAsignacion(99L);
        request.setDiaSemana("LUNES");

        when(asignacionPersonalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioPersonalService.crear(request));
        verify(horarioPersonalRepository, never()).save(any());
    }

    @Test
    void listarHorariosP(){
        HorarioPersonal h1 = new HorarioPersonal();
        h1.setAsignacion(asignacionExistente(1L));
        HorarioPersonal h2 = new HorarioPersonal();
        h2.setAsignacion(asignacionExistente(1L));

        when(horarioPersonalRepository.findAll()).thenReturn(List.of(h1, h2));

        List<HorarioPersonalResponseDTO> resultado = horarioPersonalService.listar();

        assertEquals(2, resultado.size());
        verify(horarioPersonalRepository, times(1)).findAll();
    }

    @Test
    void listarPorAsignacion(){
        HorarioPersonal h1 = new HorarioPersonal();
        h1.setAsignacion(asignacionExistente(1L));

        when(horarioPersonalRepository.findByAsignacion_IdAsignacion(1L)).thenReturn(List.of(h1));

        List<HorarioPersonalResponseDTO> resultado = horarioPersonalService.listarPorAsignacion(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdAsignacion());
    }

    @Test
    void modificarHorarioP(){
        HorarioPersonal existente = new HorarioPersonal();
        existente.setIdHorarioPersonal(1L);
        existente.setAsignacion(asignacionExistente(1L));
        existente.setDiaSemana("LUNES");
        existente.setTurno("MANANA");

        HorarioPersonalRequestDTO datos = new HorarioPersonalRequestDTO();
        datos.setIdAsignacion(1L);
        datos.setDiaSemana("VIERNES");
        datos.setHoraInicio(LocalTime.of(14, 0));
        datos.setHoraTermino(LocalTime.of(22, 0));
        datos.setTurno("TARDE");

        when(horarioPersonalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(horarioPersonalRepository.save(any(HorarioPersonal.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioPersonal.class));

        HorarioPersonalResponseDTO resultado = horarioPersonalService.modificar(1L, datos);

        assertEquals("VIERNES", resultado.getDiaSemana());
        assertEquals("TARDE", resultado.getTurno());
        assertEquals(LocalTime.of(14, 0), resultado.getHoraInicio());
    }

    @Test
    void modificarHorarioP_debeLanzarExcepcionCuandoNoExiste(){
        when(horarioPersonalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioPersonalService.modificar(99L, new HorarioPersonalRequestDTO()));
        verify(horarioPersonalRepository, never()).save(any());
    }

    @Test
    void desactivarHorarioP(){
        HorarioPersonal existente = new HorarioPersonal();
        existente.setIdHorarioPersonal(1L);
        existente.setAsignacion(asignacionExistente(1L));
        existente.setActivo(true);

        when(horarioPersonalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(horarioPersonalRepository.save(any(HorarioPersonal.class)))
                .thenAnswer(inv -> inv.getArgument(0, HorarioPersonal.class));

        HorarioPersonalResponseDTO resultado = horarioPersonalService.desactivar(1L);

        assertFalse(resultado.getActivo());
    }

    @Test
    void desactivarHorarioP_debeLanzarExcepcionCuandoNoExiste(){
        when(horarioPersonalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> horarioPersonalService.desactivar(99L));
    }

    @Test
    void eliminarHorarioP(){
        when(horarioPersonalRepository.existsById(1L)).thenReturn(true);

        horarioPersonalService.eliminar(1L);

        verify(horarioPersonalRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarHorarioP_debeLanzarExcepcionCuandoNoExiste(){
        when(horarioPersonalRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> horarioPersonalService.eliminar(99L));
        verify(horarioPersonalRepository, never()).deleteById(any());
    }
}
