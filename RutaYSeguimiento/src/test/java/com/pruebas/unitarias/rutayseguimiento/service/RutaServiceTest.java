package com.pruebas.unitarias.rutayseguimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;
import com.pruebas.unitarias.rutayseguimiento.repository.RutaRepository;

@ExtendWith(MockitoExtension.class)
public class RutaServiceTest {
    
    @Mock
    private RutaRepository repository;

    @InjectMocks
    private RutaService service;

    private Ruta ruta;

    @BeforeEach
    void setUp() {

        ruta = new Ruta();

        ruta.setId(1L);
        ruta.setEnvioId(100L);
        ruta.setOrigen("Santiago");
        ruta.setDestino("Valdivia");
        ruta.setUbicacionActual("Talca");
        ruta.setEstado("EN_RUTA");
    }

    @Test
    void deberiaCrearRuta() {

        when(repository.save(any()))
                .thenReturn(ruta);

        Ruta resultado =
                service.crearRuta(ruta);

        assertEquals(
                "EN_RUTA",
                resultado.getEstado());
    }

    @Test
    void deberiaConsultarRuta() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(ruta));

        Ruta resultado =
                service.consultarRuta(1L);

        assertNotNull(resultado);
    }

    @Test
    void deberiaActualizarUbicacion() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(ruta));

        when(repository.save(any()))
                .thenReturn(ruta);

        Ruta resultado =
                service.actualizarUbicacion(
                        1L,
                        "Temuco");

        assertEquals(
                "Temuco",
                resultado.getUbicacionActual());
    }

    @Test
    void deberiaRegistrarSeguimiento() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(ruta));

        when(repository.save(any()))
                .thenReturn(ruta);

        Ruta resultado =
                service.registrarSeguimiento(
                        1L,
                        "EN_CAMINO");

        assertEquals(
                "EN_CAMINO",
                resultado.getEstado());
    }

    @Test
    void deberiaFinalizarRuta() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(ruta));

        when(repository.save(any()))
                .thenReturn(ruta);

        Ruta resultado =
                service.finalizarRuta(1L);

        assertEquals(
                "FINALIZADA",
                resultado.getEstado());
    }

    @Test
    void deberiaEliminarRuta() {

        doNothing()
                .when(repository)
                .deleteById(1L);

        service.eliminarRuta(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiRutaNoExiste() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.consultarRuta(1L)
                );

        assertEquals(
                "Ruta no encontrada",
                exception.getMessage());
    }
}
