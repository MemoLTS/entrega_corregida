package com.tiendat.tienda.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.dto.TiendaRequestDTO;
import com.tiendat.tienda.dto.TiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.TiendaRepository;
import com.tiendat.tienda.service.TiendaService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TiendaServiceTest {

    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    @Test
    void crearTienda_debeAsignarEstadoActivaYFechaCreacion() {
        TiendaRequestDTO request = new TiendaRequestDTO();
        request.setNombre("EcoMarket Central");

        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(inv -> inv.getArgument(0, Tienda.class));

        TiendaResponseDTO resultado = tiendaService.crearTienda(request);

        assertEquals("ACTIVA", resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
        assertEquals("EcoMarket Central", resultado.getNombre());
        verify(tiendaRepository, times(1)).save(any(Tienda.class));
    }

    @Test
    void listarTiendas_debeRetornarListaDeTiendas() {
        Tienda t1 = new Tienda();
        t1.setNombre("Tienda Norte");
        Tienda t2 = new Tienda();
        t2.setNombre("Tienda Sur");

        when(tiendaRepository.findAll()).thenReturn(List.of(t1, t2));

        List<TiendaResponseDTO> resultado = tiendaService.listarTiendas();

        assertEquals(2, resultado.size());
        verify(tiendaRepository, times(1)).findAll();
    }

    @Test
    void buscarTiendaPorId_debeRetornarTienda() {
        Tienda tienda = new Tienda();
        tienda.setIdTienda(1L);
        tienda.setNombre("EcoMarket Sur");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tienda));

        TiendaResponseDTO resultado = tiendaService.buscarTiendaPorId(1L);

        assertNotNull(resultado);
        assertEquals("EcoMarket Sur", resultado.getNombre());
    }

    @Test
    void buscarTiendaPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> tiendaService.buscarTiendaPorId(99L));
    }

    @Test
    void modificarTienda_ActualizarCampos() {
        Tienda existente = new Tienda();
        existente.setIdTienda(1L);
        existente.setNombre("Nombre Viejo");
        existente.setDireccion("Direccion Vieja");
        existente.setTelefono("111111");

        TiendaRequestDTO datos = new TiendaRequestDTO();
        datos.setNombre("Nombre Nuevo");
        datos.setDireccion("Direccion Nueva");
        datos.setTelefono("999999");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(inv -> inv.getArgument(0, Tienda.class));

        TiendaResponseDTO resultado = tiendaService.modificarTienda(1L, datos);

        assertEquals("Nombre Nuevo", resultado.getNombre());
        assertEquals("Direccion Nueva", resultado.getDireccion());
        assertEquals("999999", resultado.getTelefono());
    }

    @Test
    void modificarTienda_debeLanzarExcepcionCuandoNoExiste() {
        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> tiendaService.modificarTienda(99L, new TiendaRequestDTO()));
        verify(tiendaRepository, never()).save(any());
    }

    @Test
    void desactivarTienda_ambiarEstadoAInactiva() { //Cuando existe
        Tienda existente = new Tienda();
        existente.setIdTienda(1L);
        existente.setEstado("ACTIVA");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(tiendaRepository.save(any(Tienda.class))).thenAnswer(inv -> inv.getArgument(0, Tienda.class));

        TiendaResponseDTO resultado = tiendaService.desactivarTienda(1L);

        assertEquals("INACTIVA", resultado.getEstado());
    }

    @Test
    void desactivarTienda_debeLanzarExcepcionCuandoNoExiste() {
        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> tiendaService.desactivarTienda(99L));
    }

    @Test
    void eliminarTienda_debeInvocarDeleteById() {
        when(tiendaRepository.existsById(1L)).thenReturn(true);

        tiendaService.eliminarTienda(1L);

        verify(tiendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarTienda_debeLanzarExcepcionCuandoNoExiste() {
        when(tiendaRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> tiendaService.eliminarTienda(99L));
        verify(tiendaRepository, never()).deleteById(any());
    }
}
