package com.tiendat.tienda.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.dto.AsignacionPersonalRequestDTO;
import com.tiendat.tienda.dto.AsignacionPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.TiendaRepository;
import com.tiendat.tienda.service.AsignacionPersonalService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignacionPersonalServiceTest {

    @Mock
    private AsignacionPersonalRepository asignacionPersonalRepository;

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private AsignacionPersonalService asignacionPersonalService;

    private Tienda tiendaExistente(Long id){
        Tienda tienda = new Tienda();
        tienda.setIdTienda(id);
        return tienda;
    }

    @Test
    void crearAsignacionP(){ //Asignar estado activo
        AsignacionPersonalRequestDTO request = new AsignacionPersonalRequestDTO();
        request.setIdTienda(1L);
        request.setIdEmpleado(1L);
        request.setNombreEmpleado("Juan Perez");
        request.setCargo("Vendedor");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(asignacionPersonalRepository.save(any(AsignacionPersonal.class)))
            .thenAnswer(inv -> inv.getArgument(0, AsignacionPersonal.class));

        AsignacionPersonalResponseDTO resultado = asignacionPersonalService.crearAsignacionP(request);

        assertEquals("ACTIVA", resultado.getEstadoAsignacion());
        assertEquals(1L, resultado.getIdTienda());
        verify(asignacionPersonalRepository, times(1)).save(any(AsignacionPersonal.class));
    }

    @Test
    void crearAsignacionP_debeLanzarExcepcionCuandoTiendaNoExiste(){
        AsignacionPersonalRequestDTO request = new AsignacionPersonalRequestDTO();
        request.setIdTienda(99L);
        request.setIdEmpleado(1L);
        request.setNombreEmpleado("Juan Perez");
        request.setCargo("Vendedor");

        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> asignacionPersonalService.crearAsignacionP(request));
        verify(asignacionPersonalRepository, never()).save(any());
    }

    @Test
    void listarAsignacionPersonal(){ //Retornar todas las asignaciones
        AsignacionPersonal a1 = new AsignacionPersonal();
        a1.setTienda(tiendaExistente(1L));
        AsignacionPersonal a2 = new AsignacionPersonal();
        a2.setTienda(tiendaExistente(1L));

        when(asignacionPersonalRepository.findAll()).thenReturn(List.of(a1, a2));

        List<AsignacionPersonalResponseDTO> resultado = asignacionPersonalService.listarAsignacionPersonal();

        assertEquals(2, resultado.size());
        verify(asignacionPersonalRepository, times(1)).findAll();
    }

    @Test
    void listarPorTiendaAsignacionP(){ //Retornar todas las asignaciones de esa tienda
        AsignacionPersonal a1 = new AsignacionPersonal();
        a1.setTienda(tiendaExistente(1L));

        when(asignacionPersonalRepository.findByTienda_IdTienda(1L)).thenReturn(List.of(a1));

        List<AsignacionPersonalResponseDTO> resultado = asignacionPersonalService.listarPorTiendaAsignacionP(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdTienda());
    }

    @Test
    void modificarAsignacionP(){ //Cuando existe la asignacion
        AsignacionPersonal existente = new AsignacionPersonal();
        existente.setIdAsignacion(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setNombreEmpleado("Vicente");
        existente.setCargo("Reponedor");

        AsignacionPersonalRequestDTO datos = new AsignacionPersonalRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdEmpleado(1L);
        datos.setNombreEmpleado("Vicente Aviles");
        datos.setCargo("Cajero");

        when(asignacionPersonalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(asignacionPersonalRepository.save(any(AsignacionPersonal.class)))
            .thenAnswer(inv -> inv.getArgument(0, AsignacionPersonal.class));

            AsignacionPersonalResponseDTO resultado = asignacionPersonalService.modificarAsignacionP(1L, datos);

            assertEquals("Vicente Aviles", resultado.getNombreEmpleado());
            assertEquals("Cajero", resultado.getCargo());
    }

    @Test
    void modificarAsignacionP_debeLanzarExcepcionCuandoNoExiste(){
        when(asignacionPersonalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> asignacionPersonalService.modificarAsignacionP(99L, new AsignacionPersonalRequestDTO()));
        verify(asignacionPersonalRepository, never()).save(any());
    }

    @Test
    void desactivarAsignacionP(){
        AsignacionPersonal existente = new AsignacionPersonal();
        existente.setIdAsignacion(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setEstadoAsignacion("ACTIVA");

        when(asignacionPersonalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(asignacionPersonalRepository.save(any(AsignacionPersonal.class)))
            .thenAnswer(inv -> inv.getArgument(0, AsignacionPersonal.class));

            AsignacionPersonalResponseDTO resultado = asignacionPersonalService.desactivarAsignacionP(1L);

            assertEquals("INACTIVA", resultado.getEstadoAsignacion());
    }

    @Test
    void desactivarAsignacionP_debeLanzarExcepcionCuandoNoExiste(){
        when(asignacionPersonalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> asignacionPersonalService.desactivarAsignacionP(99L));
    }

    @Test
    void eliminarAsignacionP(){
        when(asignacionPersonalRepository.existsById(1L)).thenReturn(true);

        asignacionPersonalService.eliminarAsignacionP(1L);

        verify(asignacionPersonalRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarAsignacionP_debeLanzarExcepcionCuandoNoExiste(){
        when(asignacionPersonalRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> asignacionPersonalService.eliminarAsignacionP(99L));
        verify(asignacionPersonalRepository, never()).deleteById(any());
    }

}
